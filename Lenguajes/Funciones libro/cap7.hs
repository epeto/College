--Intérprete de WAE en haskell

type Identifier = String
type Value = Int

type Env = [(Identifier, Value)]
data WAE = Num Int | Add WAE WAE | Id Identifier | With Identifier WAE WAE

--Busca el valor de la variable en el ambiente
miraArriba :: Identifier -> Env -> Value
miraArriba _ [] = error "Variable libre"
miraArriba var ((i,v):r) = if var == i 
			then v
			else miraArriba var r

--Agrega un elemento a un ambiente
extend :: Env -> Identifier -> Value -> Env
extend env i v = (i,v):env

--Intérprete
interp :: WAE -> Env -> Value
interp (Num n) env = n
interp (Add lhs rhs) env = interp lhs env + interp rhs env
interp (Id i) env = miraArriba i env
interp (With bound_id named_expr bound_body) env = interp bound_body (extend env bound_id (interp named_expr env))
