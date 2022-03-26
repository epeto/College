#lang plai

(require "grammars.rkt")
(require "parser.rkt")

;; Función que obtiene el tipo de una expresión.
;; typeof: RCFWBAE -> Type
(define (typeof exp)
	(typeof-aux exp (t-mtSub)))

;; Función auxiliar que recibe un ambiente de tipos y regresa el tipo asociado a la expresión 
;; recibida como parámetro.
;; typeof-aux: RCFWBAE Type-Env -> Type
(define (typeof-aux exp env)
  (match exp
    [(idS i) (t-lookup i env)]
    [(numS n) (tnumber)]
    [(boolS b) (tboolean)]
    [(listS (emptyLS)) (tlistof (a))]
    [(listS l) (tlistof (t-lists l env))]
    [(opS f args) (t-args f args env)]
    [(ifS expr then-expr else-expr) (cond
                                      [(not (equal? (tboolean) (typeof-aux expr env)))
                                       (error 'typeof "La condicional debe de evaluar un valor booleano")]
                                      [(not (equal? (typeof-aux then-expr env) (typeof-aux else-expr env)))
                                       (error 'typeof "Ambos casos del if deben de evaluar al mismo tipo")]
                                      [else (typeof-aux then-expr env)])]
    [(condS cases) (cond
                     [(false? (same-type (cond-exprs cases) env))
                      (error 'type-of "Todas las condiciones deben evaluar a un mismo tipo")]
                     [(false? (bool-cond cases env))
                      (error 'typeof "Las condiciones deben de usar expresiones booleanas como pruebas")]
                     [else (same-type (cond-exprs cases) env)])]
    [(withS bindings body) (typeof-aux body (tenv-extend bindings env))]
    [(withS* bindings body) (typeof-aux body (tenv-extend bindings env))]
    [(recS bindings body) (typeof-aux body (tenv-extend-soft bindings env))]
    [(funS params result body) (if (equal? result (typeof-aux body (params-new-env params env)))
                                   (t-fun params result)
                                   (error 'typeof "Tipo de resultado de la función distinto al declarado"))]
    [(appS fun-expr args) (local ([define fun-type (typeof-aux fun-expr env)])
                            (cond
                              [(not (tarrow? fun-type))
                               (error 'typeof "Se trata de aplicar algo que no es función")]
                              [(not (map-args-types fun-type args env))
                               (error 'typeof "Tipos de los argumentos no son los esperados por la función")]
                              [else (final-type fun-type)]))]))

;; Función que obtiene el tipo resultado de una función

(define (final-type type)
  (if (tarrow? type)
      (final-type (tarrow-result type))
      type))

;; Función que toma un tipo, que debe ser una función, y una lista de expresiones
;; y regresa si ambos se corresponden uno a uno

(define (map-args-types type args env)
  (cond
    [(and (equal? 1 (length args)) (not (tarrow? type))) (error 'typeof "Número de argumentos mayor al esperado")]
    [(equal? 1 (length args)) (if (equal? (tarrow-arg type) (typeof-aux (car args) env))
                                 true
                                 false)]
    [else (if (equal? (tarrow-arg type) (typeof-aux (car args) env))
                                 (map-args-types (tarrow-result type) (cdr args) env)
                                 false)]))

;; Función auxiliar que toma una lista de parámetros y un resultado final,
;; ambos asociados a una función y regresa el tipo correspondiente

(define (t-fun params result )
  (cond
    [(equal? 1 (length params)) (tarrow (param-tipo (car params)) result)]
    [else (tarrow (param-tipo (car params)) (t-fun (cdr params) result))]))

;; Función auxiliar que toma una lista de parámetros y un ambiente
;; de tipos y regresa el ambiente con todas las asignaciones correspondientes

(define (params-new-env params env)
  (cond
    [(empty? params) env]
    [else (t-aSub (param-name (car params)) (param-tipo (car params)) (params-new-env (cdr params) env))]))

;; Función auxiliar que toma una lista de bindings y un ambiente
;; de tipos y regresa el ambiente con todas las asignaciones
;; de tipos correspondientes, no revisa antes que cada valor corresponda
;; al tipo con el que se declaró

(define (tenv-extend-soft binds env)
  (cond
    [(empty? binds) env]
    [else  (tenv-extend (cdr binds) (t-aSub (bindingS-name (car binds)) (bindingS-tipo (car binds)) env))]))


;; Función auxiliar que toma una lista de bindings y un ambiente
;; de tipos y regresa el ambiente con todas las asignaciones
;; de tipos correspondientes, antes revisa que cada valor corresponda
;; al tipo con el que se declaró, es importante notar que esta función
;; sirve para el with* porque evalua los tipos de las declaraciones en el
;; ambiente que va siendo creado de las declaraciones anteriores

(define (tenv-extend binds env)
  (cond
    [(empty? binds) env]
    [(not (equal? (bindingS-tipo (car binds)) (typeof-aux (bindingS-value (car binds)) env)))
     (error 'typeof "El tipo del valor no corresponde al declarado en el with")]
    [else  (tenv-extend (cdr binds) (t-aSub (bindingS-name (car binds)) (bindingS-tipo (car binds)) env))]))

;; Función auxiliar que toma una lista de condicionales y revisa si
;; las expresiones de evaluación de sus miembros evaluan a tipo tboolean

(define (bool-cond conds env)
  (cond
    [(and (equal? 1 (length conds)) (condition? (car conds))) (if (equal? (tboolean) (typeof-aux (condition-expr (car conds)) env))
                                                                  true
                                                                  false)]
    [(and (equal? 1 (length conds)) (else-cond? (car conds))) true]
    [else (if (and (bool-cond (cdr conds) env) (equal? (tboolean) (typeof-aux (condition-expr (car conds)) env)))
                                                                  true
                                                                  false)]))

;; Función auxiliar que toma una lista de condiciones y regresa una lista
;; de expresiones que corresponden a las expresiones que regresarían las
;; condiciones en caso de que sus condiciones se evaluaran a true

(define (cond-exprs conds)
  (cond
    [(empty? conds) '()]
    [(condition? (car conds)) (cons (condition-then-expr (car conds)) (cond-exprs (cdr conds)))]
    [else (cons (else-cond-else-expr (car conds)) (cond-exprs (cdr conds)))]))

;; Función auxiliar que examina los tipos de los miembros de una lista

(define (t-args f args env)
  (cond
    [(equal? f consV-expr) (if (tlistof? (typeof-aux (car args) env)) 
                    (tlistof-tipo (typeof-aux (car args) env))
                    (error 'typeof "se esperaba una lista no vacia como argumento"))]
    [(equal? f consV-xs) (if (tlistof? (typeof-aux (car args) env)) 
                    (typeof-aux (consLS-xs (listS-l (car args))) env)
                    (error 'typeof "se esperaba una lista no vacia como argumento"))]
    [(equal? f emptyV?) (if (tlistof? (typeof-aux (car args) env)) 
                  (tboolean)
                  (error 'typeof "se esperaba una lista no vacia como argumento"))]
    [else (local ([define types (t-op f)]
                  [define tfirst (typeof-aux (car args) env)])
            (if (equal? (car types) (same-type args env))
                (cadr types)
                (error 'typeof (format "Se esperaban argumentos de tipo ~a" (car types)))))]))

;; Funcion que recibe una lista de expresiones y dice si son de tipo homogéneo
;; si son del mismo tipo regresa el tipo, si no regresa false 

(define (same-type expr-lst env)
  (cond
    [(equal? 1 (length expr-lst)) (typeof-aux (car expr-lst) env)]
    [else (if (equal? (typeof-aux (car expr-lst) env) (same-type (cdr expr-lst) env))
              (typeof-aux (car expr-lst) env)
              false)]))    
    
;; Función auxiliar que asigna un tipo esperado y un tipo de regreso
;; a cada operación del lenguaje

(define (t-op f)
  (cond 
    [(equal? f nasty-or) (list (tboolean) (tboolean))]
    [(equal? f nasty-and) (list (tboolean) (tboolean))]
    [(equal? f <) (list (tnumber) (tboolean))]
    [(equal? f <=) (list (tnumber) (tboolean))]
    [(equal? f >) (list (tnumber) (tboolean))]
    [(equal? f >=) (list (tnumber) (tboolean))]
    [(equal? f =) (list (tnumber) (tboolean))]
    [(equal? f nasty-not-equal) (list (tnumber) (tboolean))]
    [(equal? f not) (list (tboolean) (tboolean))]
    [(equal? f consV-expr) (list (tlistof (a)) (a))]
    [(equal? f consV-xs) (list (tlistof (a)) (tlistof (a)))]
    [(equal? f emptyV?) (list (tlistof (a)) (tboolean))]
    [(equal? f zero?) (list (tnumber) (tboolean))]
    [else (list (tnumber) (tnumber))]
    ))

;; Función auxiliar que examina los tipos de los miembros de una lista

(define (t-lists lista env)
  (match lista
    [(emptyLS) (a)]
    [(consLS expr xs) (local ([define thead (typeof-aux expr env)]
                              [define ttail (t-lists (listS-l xs) env)])
                        (if (or (equal? thead ttail)
                                (equal? (a) ttail))
                            thead
                            (error 'typeof "listas deben ser homogéneas")))]))

;; Lookup definido para buscar en el ambiente de tipos

(define (t-lookup id env)
   (match env
      [(t-mtSub) (error 't-lookup (format "~a No es del tipo esperado" id))]
      [(t-aSub sub-id type rest-env)
         (if (symbol=? id sub-id)
             type
             (t-lookup id rest-env))]))
