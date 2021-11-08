Estamos pensando en un nuevo juego virtual de carreras de autos en donde el jugador A corre contra otros jugadores. La posibilidad de que el jugador A gane es G y de perder 1-G.

El juego involucra N carreras independientes. Se van a aceptar apuestas únicamente del tipo el jugador A gana la carrera 1 y pierde las otras N - 1, A pierde la carrera 1 y gana las otras N - 1. Permitiendo combinar entre todos los resultados de los eventos respetando el orden.

**Aquí asumo que el valor de G está acotado entre 0 y 1, y sería el resultado de la probabilidad sería el acumulado entre el total de carreras, o sea si tengo tres carreras y solo hay dos apuestas por el jugador A, entonces la probabilidad resultante de las apuestas sería (2 / 3 = 0,666). Se asume que en una apuesta se gana (1) o se pierde (0).**

**Teniendo en cuenta que pueden ser M jugadores para N carreras, entonces podemos crear una manera simple de apostar donde se diga cual jugador es el elegido y a que resultado se le apuesta. De esta menra podemos minimizar la captura de los datos y simplificar el problema. Asi podemos usar estas posibles entradas como ejemplo:**

*Apuesta 1. Jugador X Gana carrera N (el resto las pierde)<br/>*
*Apuesta 2. Jugador Y Pierde carrera M (el resto las gana)<br/>*
...

**Esto genera una matriz por cada apostador que entonces sería usada para calcular la probabilidad obtenida por cada jugador para ganar en una carrera determinada. Así tenemos como resultado una matriz jugador/carrera con los valores obtenidos de las apuestas realizadas.**<br/>

Por ejemplo, con las 3 apuestas siguientes para dos jugadores y 3 carreras tendríamos el siguiente resultado:

**A1: J1 gana C1 y pierde las demás (implica que J2 gana entonces C2 y C3).<br/>
A2: J2 gana C1 y pierde las demás (implica que J1 gana entonces C2 y C3).<br/>
A3: J1 gana C2 y pierde las demás (implica que J2 gana entonces C1 y C3).<br/>**

                Numero de apuestas = 3
           A1      |     A2      |      A3
       C1  C2  C3  | C1  C2  C3  |  C1  C2  C3
    J1  1   0   0  |  0   1   1  |   0   1   0
    J2  0   1   1  |  1   0   0  |   1   0   1

Las apuestas anteriores dan la matriz combinada siguiente:

                        Numero de apuestas = 3
                C1                      C2                    C3
    J1      A1 / 3    = 0,333  (A2 + A3) / 3 = 0,666       A2 / 3   = 0,333
    J2  (A2 + A3) / 3 = 0,666     A1 / 3     = 0,333  (A1 + A3) / 3 = 0,666

1) Lo que se pide es implementar una api rest con un servicio **devolver_probabilidades(G (Probabilidad de Ganar), N (Cantidad de partidos))** que devuelva en alguna estructura todas las posibilidades que existen para los N partidos.
   <br/>
   <br/>
   Interpretación 1: la representación de los parámetros de entrada serían los siguientes:<br/>
      • G: Objeto JSON con la siguiente estructura<br/>
        [{jugador: #, apuesta: [0, 1], carrera: #}, … ]<br/>
      • N: Número de carreras<br/>
      <br/>
      **Para este caso es preferible usar una estructura enlazada tipo *Lista de Listas* que permita trabajar de manera mas efectiva la matriz que un árbol**
      <br/>
      <br/>
   Interpretación 2: la representación de los parámetros de entrada serían los siguientes:<br/>
      • G: Objeto JSON con la siguiente estructura<br/>
        [{jugador: #, apuesta: [0, 1], carrera: #}, … ]<br/>
      • N: Número de carreras<br/>
      <br/>
      **Para este caso es preferible usar una estructura enlazada tipo *Lista de Listas* que permita trabajar de manera mas efectiva la matriz que un árbol**
      <br/>
      <br/>
      Hint: Se recomienda utilizar un árbol para resolver las posibilidades. En donde cada nodo tiene dos hijos, uno con la probabilidad de ganar ese evento y otro con la de perder, sumando en cada nodo las posibilidades hasta la raíz.
   

2) Publicar la solucion en alguna plataforma cloud.
3) Escribir en una base de datos cada resultado.


playerRace
   name

playerBet
   playerRace
   bet (win/lost)