# Rebote
Este es el proyecto grupal de la asignatura Programación III.

## Fichero de configuración (config.properties)
En este fichero encontrarás varios ajustes para la aplicación para su correcto funcionamiento.
Podrás cambiar las siguientes cosas:
  - Nombre de la aplicación.
  - Usar o no datos de prueba al iniciar la aplicación.
  - Permitir o no edición de datos con ficheros CSV.
  - Cambiar mensajes de error de la base de datos y ficheros CSV.

## Funcionamiento
Al iniciar la aplicación verás un panel de carga (en el que se carga la configuración elegida) y posteriormente te llevará automaticamente a una ventana de inicio.
En esta ventana dependiendo de la configuración que hayas elegido (si quieres utilizar ficheros CSV o no) encontrarás 2 o 4 (en caso de utilizar ficheros CSV) botones.
Estos se utilizan para lo que indica su nombre:
  - Iniciar sesión
  - Registrarse
  - Exportar a fichero (exportar los datos de la base de datos a ficheros)
  - Importar desde fichero (importar los datos de los ficheros a la base de datos)

Estos dos últimos botones se utilizarán para la edición de datos más cómoda simplemente editando ficheros CSV. De esta manera, cualquier interacción con la base de datos se simplifica considerablemente. Esto es debido a que en vez de tener que trabajar con una base de datos mediante métodos, con el botón "Exportar a fichero" conseguirás varios documentos CSV que editando a mano realizan la misma función al importarlos con el botón "Importar desde fichero".

En caso de que hayas elegido en la configuración utilizar datos de prueba, esta opción habrá guardado varios usuarios y entrenadores con los que puedes iniciar sesión para probar la aplicación.
### Para iniciar como jugador
Puedes utilizar las siguientes credenciales de prueba:<br />
Usuario: unai.player<br />
Contraseña: prueba5

### Para iniciar como entrenador
Puedes utilizar las siguientes credenciales de prueba:<br />
Usuario: unai.trainer<br />
Contraseña: prueba5

### Para poder registrar partidos
Puedes utilizar las siguientes credenciales de prueba:<br />
Usuario: admin<br />
Contraseña: admin
    
En caso contrario, si quieres entrar por primera vez a la aplicación tendrás que registrarte dandole al botón "Registrarse", y añadiendo tus datos personales en la ventana que se abrirá. Las siguientes veces que quieras acceder, te bastará con iniciar sesión con el botón "Iniciar sesión" e introducir tu nombre de usuario y contraseña que hayas elegido al registrarte.

## Si has iniciado como jugador
Llegarás a un menú principal en el que con las pestañas que se ven arriba podrás interactuar como un menú y acceder al apartado que más te interese; además de un botón en la parte inferior para cerrar la sesión, es decir, volver a la pantalla de inicio. Los apartados son los siguientes:
  - Tu perfil
  - Tu equipo
  - Buscador
  - Partidos

En "Tu perfil" podrás ver y modificar tus datos (nombre de usuario, nombre, apellidos...).
En "Tu equipo" podrás ver datos y estadísticas del equipo al que perteneces, además de tener la posibilidad de abandonarlo con el botón "Salir del equipo". En caso de no pertenecer a ningún equipo aparecerá un texto indicando que no perteneces a ningún equipo, y podrás unirte a uno en la siguiente pestaña.
En "Buscador" podrás ver todos los equipos de la liga y filtrarlos por su nombre. Además, si haces clic en alguno de ellos te abrirá una nueva ventana donde podrás ver datos, estadísticas y sus partidos jugados. Otro componente que encontrarás será el botón "Unirse", este se utilizará para ingresar a un equipo tanto si venimos de otro como si no; junto a este también encontrarás el botón "Atrás" el cual te llevará de vuelta a "Tu perfil".
En "Partidos" podrás ver los datos de los partidos que ha jugado el equipo en el que estás. En el caso de no estar unido a ningún equipo esta pestaña no aparecerá visible hasta que entres a uno mediante el buscador como se ha explicado anteriormente.

## Si has iniciado como entrenador
Podrás hacer exactamente lo mismo que un jugador con un añadido, la posibilidad de editar los datos de tu equipo en la pestaña de "Tu equipo", en la que un jugador solo podría visualizar.

## Si has iniciado como admin
Al haber iniciado sesión como administrador serás el único que puede registrar partidos, estos son generados automaticamente y se visualizar divididos por semanas, lo que representa una liga. Debajo de todos los partidos con sus jornadas tienes la posibilidad de generar nuevamente los partidos de la liga con el botón "Generar de nuevo" o utilizando el botón "Atrás" volverás al inicio.
Al hacer clic en el partido que deseemos registrar se abrirá una ventana en la que se perciben dos componentes principales:
  - Una tabla en la que apareceran todas las acciones que se registren
  - Un campo de baloncesto con dos textos al lado derecho indicando donde anota cada equipo

Si haces clic al botón con el icono de la pregunta se te mostrará un texto indicando el funcionamiento de la ventana, junto a este botón está el botón "Finalizar" en el que harás clic cuando desees terminar de registrar el partido.
Cada vez que quieras ingresar una acción tendrás que hacer clic aproximadamente en la zona que ha ocurrido y se te abrirá una ventana en la que tendrás que rellenar los datos que te pide paso a paso. Cuando acabes, solo tendrás que pulsar el botón "Confirmar". En caso de que hayas elegido una acción del tipo "Falta" se te abrirá otra ventana en la que se te preguntará si esta acción ha generado tiros libres o no, para responderle simplemente haz clic en el botón al que corresponda tu respuesta. En caso de haber indicado que ha ocurrido algún tiro libre se abrirá una ventana por cada tiro libre en la que como en la ventana de registro anterior simplemente tendrás que rellenar los espacios que se te piden.
Como todas las acciones tienen consecuencias, en caso de tú haber expulsado (eligiendo el tipo de acción "Expulsión") o que se haya expulsado automaticamente (por acumulación de 5 faltas), no podrás seleccionar a este jugador como autor de ninguna otra acción posterior.
En todo momento haciendo uso del teclado Ctrl + Z podrás deshacer la última acción registrada.
En la tabla dependiendo del tipo de acción se te mostrarán los datos de un color u otro.
