#lang plai
(require "streams.rkt")

(define (fib n)
   (if (<= n 1)
       n
       (+ (fib (- n 1)) (fib (- n 2)))))
    
(define (genera-fib n)
  (scons (fib n) (thunk (genera-fib (+ n 1)))))

(define (sfilter f s)
   (match s
      [(sempty) s]
      [(scons h t) (if (f h)
                       (scons h (thunk (sfilter f (t))))
                       (sfilter f (t)))]))

(define (sappend s1 s2)
  (match s1
    [(sempty) s2]
    [(scons h t) (scons h (thunk (sappend (t) s2)))]))

(define (list->stream lst)
  (match lst
    ['() sempty]
    [(cons x xs) (scons x (thunk (list->stream xs) ))]))


(define s1 (scons 1 (thunk (scons 2 (thunk (sempty))))))
(define s2 (scons 3 (thunk (scons 4 (thunk (sempty))))))
;;(define s1 (list->stream '(1 2 3)))
;;(define s2 (list->stream '(4 5 6)))