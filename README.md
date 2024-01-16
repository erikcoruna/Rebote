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
