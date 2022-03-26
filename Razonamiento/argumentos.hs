
-- Programa para leer argumentos de la línea de comandos.

import System.Environment
import Data.List

main = do
   args <- getArgs
   progName <- getProgName
   let primArg = head args
   putStrLn ("El primer argumento es"++primArg)
   putStrLn "The arguments are:"
   mapM putStrLn args
   putStrLn "The program name is:"
   putStrLn progName
