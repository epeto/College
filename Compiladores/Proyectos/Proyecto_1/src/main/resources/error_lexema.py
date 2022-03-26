#FIZZBUZZ hata 30
#ERROR LEXEMA

contador = 1
while(contador < 30):
    if contador % 3 == 0:
    	print "fiz" @

    if contador % 5 == 0:
        print "buzz"
    
    if contador % 5 != 0 and contador % 3 != 0:
        print contador

    contador = contador + 1