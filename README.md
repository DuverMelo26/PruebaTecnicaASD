# Prueba Técnica ASD - Aplicación de Películas

Esta aplicación muestra una lista de películas populares y sus detalles, usando Jetpack Compose y una arquitectura limpia basada en el patrón MVVM.

Autor: Duver Alexander Melo Mendivelso.

# Tecnologías utilizadas

 - Kotlin.
 - Jetpack Compose.
 - ViewModel + LiveData.
 - Hilt (Inyección de Dependencias).
 - Navigation Compose.
 - Retrofit.
 - Coil (Carga de imágenes).
 - Coroutines.
 - Pruebas unitarias (ioMockk) y pruebas instrumentadas.
 - Arquitectura limpia dividida en capas: data, domain y presentation.

# Instrucciones para ejecutar la aplicación

1. Descromprimir el .zip o clonar el repositorio: https://github.com/DuverMelo26/PruebaTecnicaASD
2. Abrir el proyecto en Android Studio.
3. Acceder al sitio web de The Movie DB: https://www.themoviedb.org/.
4. Crear una cuenta o iniciar sesión.
5. Verificar la cuenta desde el correo electrónico si es nueva.
6. Ir a la foto de perfil (parte superior derecha), hacer clic y seleccionar "Ajustes".
7. En el menú lateral izquierdo, seleccionar la opción "API".
8. Hacer clic en "Solicitar una clave de API" y completar el formulario.
9. Dar click en el enlace Access your API key details here o volver a hacer el paso 6.
10. Copiar la API KEY proporcionada.
11. Pegar la API KEY en el archivo "local.properties" en una variable llamada API_KEY, puede seguir este ejemplo

   ```
   API_KEY=jsahjdasjh34432
   ```

12. Ejecutar la aplicación desde Android Studio.

# Explicación de las decisiones técnicas

1. Pantalla Principal
   - Se utilizó la API pública de The Movie DB para obtener un listado paginado de películas populares y detalles de cada película.
   - La pantalla principal PeliculasPopularesScreen se desarrolló con Jetpack Compose.
   - Se implementó un campo TextField que permite filtrar la lista en tiempo real según el título de la película.

2. Pantalla Detalle
   - Se creó una segunda pantalla para mostrar detalles de una película seleccionada.
   - Esta pantalla también está construida con Jetpack Compose y obtiene los datos desde la API a partir del ID de la película recibido desde la navegación.

3. Integración de SDK Externos
   - Las bibliotecas de terceros usadas fueron:
      * Retrofit: Usada para hacer las llamadas HTTP
      * Convert-gson: Permite convertir los JSON de las llamadas HTTP de Retrofit en objetos
      * Hilt: Libreria que brinda las etiquetas y clases necesarias para implementar inyección de dependencias entre clases.
      * Coil: Permite cargar imagenes de URLs en Jetpack compose
      * Navigation Compose: Permite la navegación entre pantallas Compose
      * ioMockk: Libreria que permite crear objetos Mockeados para las pruebas unitarias
   - La libreria de COIL permitió cargar imágenes en Jetpack Compose, se uso para cargar los poster de cada película traidas desde la URL de TheMovieDB.

4. Test
- Los test unitarios que generan más valor se encuentran en la carpeta com.example.pruebatecnica.peliculas.domain (test) en la carpeta de casos de uso, esto porque al testear los casos de uso se permite apreciar la arquitectura limpia con su separación por capas, la lógica de negocio de cada use case y el correcto uso de mockeos para controlar los casos de exito y fallo.
- Las pruebas unitarias para la lógica de negocio se hicieron en la carpeta com.example.pruebatecnica.peliculas (test), donde se le hicieron pruebas por separado a cada caso de uso y al viewModel del proyecto.
- La prueba instrumental que permite ver la interacción entre pantallas se encuentra en la capeta presentation del apartado AndroidTest y la prueba consiste en:
   * Iniciar en la pantalla de películas populares
   * Esperar a que el loader que indica que la consulta de películas se está haciendo desaparezca
   * Validar que existe en pantalla el Buscador de películas por texto
   * Hacer un scroll 3 veces en la lista de películas hacia abajo
   * Simular que se escribe el nombre de una película en el buscador letra por letra para que se filtren las películas por su titulo
   * Se espera a que carguen las películas filtradas y se valida que exista al menos una película listada
   * Se le da click al primer elemento de la lista de películas filtrada
   * Se abre la pantalla de detalles de la película selecciona, en esta pantalla como se consultan los datos de la película se debe esperar a que el loader de consulta se oculte
   * Se deja ver la pantalla con los datos de la película por 2 segundos
   * Se oprime el boton de salir de la pantalla de detalles para devolverse a la pantalla de películas populares
   * Se espera hasta que la pantalla se dibuje y aparezca el TextField que filtra películas
   * Se deja ver la pantalla de lista de películas por 2 segundos antes de culminar el test

- Para realizar el test instrumentado se utilizaron Tags que ayudaban a identificar las vistas de las pantallas, de esta forma tambien se pudieron identificar los elementos de la lista al filtrar las películas, logrando que se diera click sobre el primer elemento que tuviera una coincidencia de tag en la pantalla.