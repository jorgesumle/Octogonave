## Instalación

Puedes descargarte la última versión del videojuego a partir del último fuente disponible.
Sin embargo, se recomienda descargar una versión estable. Para ver las últimas versiones estables
ve a https://github.com/jorgesumle/Octogonave/releases y descarga el archivo comprimido.
En él encontrarás además del .jar un archivo .xml y una carpeta que contiene archivos de idiomas .lang.
Para que el programa funcione todos los archivos deben estar en la misma carpeta. Ejecutar directamente
el .jar debería funcionar en la mayoría de sistemas operativos si Java 8 o superior está instalado correctamente. Si no, ve a la ruta donde se encuentran los archivos y ejecuta el comando `java -jar Octogonave.jar`. En caso de 
que no funcione, deberías comprobar cuál es tu versión de Java. Si estás usando OpenJDK deberás instalar lo necesario
para que funcione JavaFX correctamente.

## Instrucciones

Tu personaje es una nave espacial un poco extraña: se trata de una nave con
forma de octógono llamada Octogonave. Tu objetivo es conseguir el mayor número de
puntos que puedas; para ello debes sobrevivir esquivando y destruyendo asteroides y OVNIs.
Si chocas con los bordes de la ventana (la del sistema operativo) o con algún asteroide u OVNI,
el escudo deflector de la nave se destruye. Si la nave no tiene escudos y vuelve a chocar,
se destruye y acaba la partida. Puedes recoger gemas espaciales y una bonificación para reducir el
tiempo de recarga de la nave momentaneamente. 

## Controles durante la partida

Teclas | Acción
------ | ------
FLECHA ARRIBA | Mueve la nave arriba
FLECHA ARRIBA + FLECHA DERECHA | Mueve la nave arriba y a la derecha
FLECHA DERECHA | Mueve la nave a la derecha
FLECHA DERECHA + FLECHA ABAJO | Mueve la nave abajo y a la derecha
FLECHA ABAJO | Mueve la nave abajo
FLECHA ABAJO + FLECHA IZQUIERDA | Mueve la nave abajo y a la izquierda
FLECHA IZQUIERDA | Mueve la nave a la izquierda
FLECHA IZQUIERDA + FLECHA ARRIBA | Mueve la nave arriba y a la izquierda
Z | Disminuye la velocidad
X | Aumenta la velocidad
W | Dispara arriba
W + D | Dispara arriba a la derecha
D | Dispara a la derecha
D + S | Dispara abajo a la derecha
S | Dispara abajo
S + A | Dispara abajo a la izquierda
A | Dispara a la izquierda
A + W | Dispara arriba a la izquierda
P | Pausa la partida
G | Accede al menú para guardar la partida. Solo puede accederse a este menú cuando el juego está en pausa, y si el jugador se encuentra en la transición de nivel.
