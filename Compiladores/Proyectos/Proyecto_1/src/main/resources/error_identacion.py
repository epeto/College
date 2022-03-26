#FIZZBUZZ hata 30
#ERROR IDENTACIÓN

contador = 1
while(contador < 30):
    if contador % 3 == 0:
        print "fizz"

    if contador % 5 == 0:
   print "buzz"
    
    if contador % 5 != 0 and contador % 3 != 0:
        print contador

    contador = contador + 1