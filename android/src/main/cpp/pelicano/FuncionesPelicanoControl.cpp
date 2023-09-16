// 0. --------------- ESTRUCTURAS DE RESPUESTA --------------------//

struct Response_t{
    int StatusCode;
    std::string Message;
};

struct CoinError_t{
    int StatusCode;
    int Event;
    int Coin;
    std::string Message;
    int Remaining;
};

struct CoinLost_t{
    int CoinCinc;
    int CoinCien;
    int CoinDosc;
    int CoinQuin;
    int CoinMil;
};

struct TestStatus_t{
    std::string Version;
    int Device;
    int ErrorType; 
    int ErrorCode;
    std::string Message; 
    std::string AditionalInfo;
    int Priority; 
};

// 1. --------------- VALIDADOR DE MONEDAS PELICANO --------------------//

/**
 * @brief Funcion que corre los primeros estados de la maquina de estados y deja listo el dispositivo para las siguientes operaciones
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje

 * @return 200 - Validador OK. Todos los sensores reportan buen estado
 * @return 301 - Alerta en el validador. Hay algo presente en la bandeja
 * @return 302 - Alerta en el validador. La puerta de la bandeja esta abierta
 * @return 404 - DefaultError
 * @return 501 - Fallo con el validador. Sensor bajo esta bloqueado
 * @return 502 - Fallo con el validador. Sensor alto esta bloqueado
 * @return 503 - Fallo con el validador. No responde
 * @return 504 - Fallo en el codigo del validador. Revisar codigo en C
 * @return 505 - Fallo en la conexion del validador. Puerto no encontrado
 */
Response_t Connect();

/**
 * @brief Funcion que revisa el estado del validador
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 200 - Validador OK. Todos los sensores reportan buen estado
 * @return 301 - Alerta en el validador. Hay algo presente en la bandeja
 * @return 302 - Alerta en el validador. La puerta de la bandeja esta abierta
 * @return 404 - DefaultError
 * @return 501 - Fallo con el validador. Sensor bajo esta bloqueado
 * @return 502 - Fallo con el validador. Sensor alto esta bloqueado
 * @return 503 - Fallo con el validador. No responde
 * @return 504 - Fallo en el codigo del validador. Revisar codigo en C
 */
Response_t CheckDevice();

/**
 * @brief Funcion que inicia la lectura de monedas en el validador
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 201 - Validador OK. Listo para iniciar a leer monedas
 * @return 202 - Start reader corrio nuevamente. Listo para iniciar
 * @return 301 - Alerta en el validador. Hay algo presente en la bandeja
 * @return 302 - Alerta en el validador. La puerta de la bandeja esta abierta
 * @return 404 - DefaultError
 * @return 501 - Fallo con el validador. Sensor bajo esta bloqueado
 * @return 502 - Fallo con el validador. Sensor alto esta bloqueado
 * @return 503 - Fallo con el validador. No responde
 * @return 504 - Fallo en el codigo del validador. Revisar codigo en C
 * @return 506 - Fallo con el validador. Validador no reinicio aunque se intento reiniciar
 */
Response_t StartReader();

/**
 * @brief Funcion que busca la ultima moneda leida
 * @return CoinError_t Estructura que contiene un codigo de estado, un evento (0-255) (para identificar si es una nueva moneda), un valor de moneda ('0' si es error), un mensaje asociado y los eventos faltantes
 * @return 202 - Evento actual - Moneda Actual - Moneda detectada - Eventos faltantes 
 * @return 302 - Evento actual - 0             - Moneda rechazada - Eventos faltantes
 * @return 303 - Evento actual - 0             - No hay nueva informacion - Eventos faltantes
 * @return 401 - Evento actual - 0             - Codigo: [Codigo de error especifico] Mensaje: [Mensaje de error especifico] CC: [Contador de criticos] WC: [Contador de warnings] - Eventos faltantes
 * @return 402 - Evento actual - 0             - Codigo: [Codigo de error especifico] Mensaje: [Mensaje de error especifico] CC: Full WC: [Contador de warnings] - Eventos faltantes
 * @return 403 - Evento actual - 0             - Codigo: [Codigo de error especifico] Mensaje: [Mensaje de error especifico] CC: [Contador de criticos] WC: Full - Eventos faltantes
 * @return 404 - 0             - 0             - DefaultError - 0
 * @return 503 - Evento actual - 0             - Fallo con el validador. No responde - Eventos faltantes
 * @return 507 - 0             - 0             - No se ha iniciado el lector (StartReader) - 0
 */
CoinError_t GetCoin();

/**
 * @brief Funcion que se debe correr una vez que GetCoin reporta mas de 1 evento faltante. Escanea las monedas que quedaron por leer
 * @return CoinLost_t Estructura que contiene la cantidad de monedas de 50, de 100, de 200, de 500 y de 1000
 * @return Monedas de 50 - Monedas de 100 - Monedas de 200 - Monedas de 500 - Monedas de 1000
 */
CoinLost_t PelicanoControlClass::GetLostCoins(); 

/**
    Canales del validador Pelicano

    {1,1000},   //Vieja
    {2,500},    //Vieja
    {3,200},    //Vieja
    {4,100},    //Vieja 
    {5,50},     //Vieja   
    {6,0},     
    {7,0},     
    {8,0},
    {9,0},  
    {10,0},  
    {11,0}    
    {12,1000}, //Nueva
    {13,500},  //Nueva
    {14,200},  //Nueva
    {15,100},  //Nueva
    {16,50},   //Nueva

 * @brief Funcion que cambia la mascara de inhibicion de los canales a desactivar
 * @brief Si la mascara de inhibicion = 255 (11111111), todos los canales estaran activados
 * @brief Si la mascara de inhibicion = 240 (11110000), se inhibiran los 4 ultimos canales
 * @param InhibitMask1 Mascara de inhibicion de los canales 1 al 8 (0 = Canal Inhibido / 1 = Canal no inhibido)
 * @param InhibitMask2 Mascara de inhibicion de los canales 9 al 16 (0 = Canal Inhibido / 1 = Canal no inhibido)
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 203 - Validador OK. Canales inhibidos correctamente
 * @return 404 - DefaultError
 * @return 508 - Fallo con el validador. No se pudieron inhibir los canales
 */
Response_t ModifyChannels(int InhibitMask1,int InhibitMask2);

/**
 * @brief Funcion que detiene la lectura de monedas en el validador
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 200 - Validador OK. Todos los sensores reportan buen estado
 * @return 301 - Alerta en el validador. Hay algo presente en la bandeja
 * @return 302 - Alerta en el validador. La puerta de la bandeja esta abierta
 * @return 404 - DefaultError
 * @return 405 - No se puede detener el lector porque no se ha iniciado
 * @return 501 - Fallo con el validador. Sensor bajo esta bloqueado
 * @return 502 - Fallo con el validador. Sensor alto esta bloqueado
 * @return 503 - Fallo con el validador. No responde
 * @return 504 - Fallo en el codigo del validador. Revisar codigo en C
 * @return 506 - Fallo con el validador. Validador no reinicio aunque se intento reiniciar
 * @return 509 - Fallo en el deposito. Hubo un error critico
 * @return 510 - Fallo con el validador. Validador no limpio el bowl, aunque se intento limpiar
 */
Response_t StopReader();

/**
 * @brief Funcion que envia un reset al validador
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 204 - Validador OK. Reset corrio exitosamente
 * @return 404 - DefaultError
 * @return 506 - Fallo con el validador. Validador no reinicio aunque se intento reiniciar
 */
Response_t ResetDevice();

/**
 * @brief Funcion que corre la rutina de vaciado de basura
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 205 - Validador OK. Cleanbowl corrio exitosamente
 * @return 404 - DefaultError
 * @return 510 - Fallo con el validador. Validador no limpio el bowl, aunque se intento limpiar
 */
Response_t CleanDevice();

/**
 * @brief Funcion que corre la rutina de vaciado de basura
 * @return Response_t Estructura que contiene un codigo de estado y un mensaje
 * @return 206 - Validador OK. Monedas insertadas: + [InsCoins]
 * @return 404 - DefaultError
 * @return 511 - Fallo con el validador. GetInsertedCoins no pudo correr
 */
Response_t GetInsertedCoins();

/**
    "Version":"1.0",
    "Device":"1",
    "ErrorType":"1",
    "ErrorCode":"10",
    "Message":"Null event",
    "Priority":"1",
    "AditionalInfo":"Previous error solved",
    "Date":"11/05/2023 11:55" [Se adiciona desde el front]

 * @brief Funcion que entrega el ultimo estado del validador
 * @return TestStatus_t Estructura que contiene la version del codigo, el numero de dispositivo, el ultimo tipo de error, el ultimo codigo de error, el ultimo mensaje de error, 
 * @return la prioridad del error y una informacion adicional
 * @return Posibles codigos y mensajes en el archivo "DataErrors.cpp"
 */
TestStatus_t TestStatus();

/*
// 2. --------------- PANTALLA DE MANTENIMIENTO--------------------//

Validador Pelicano

1. Boton para revisar conexion -> Connect()
2. Revisa estado general, autorevision y leer los opto estados -> CheckDevice() y luego TestStatus()
3. Reset al dispositivo  -> ResetDevice()
4. Corre rutina de vaciado de basura -> CleanDevice()
5. Revisar vida util -> GetInsertedCoins(), luego aplicar la siguiente formula:

float VidaUtil = 100.0 - ((PelicanoControlObject.InsertedCoins*100.0) / 750000.0);

Para obtener la vida restante porcentual del Pelicano

6. Iniciar a leer monedas -> StartReader() y luego GetCoin()/GetLostCoins()
	Cantidad de monedas aceptadas/ingresadas/rechazadas
7. Terminar de leer monedas -> StopReader()

//3. --------------- FLUJO DEL VALIDADOR --------------------//

1. Validador no conectado

- La revision de rutina de cada minuto y la que se hace antes de mostrar las opcion de deposito al usuario deberian mandar el Oink a mantenimiento [Solo cambiar de pantalla de mantenimiento a normal desde el dashboard y no reiniciando el Oink]
- La maquina deberia enviar un correo al detectar que no esta conectado

2. Validador conectado

- La revision de rutina de cada minuto y la que se hace antes de mostrar las opciones deberian mandar el Oink a mantenimiento [Solo cambiar de pantalla de mantenimiento a normal desde el dashboard y no reiniciando el Oink]
- La maquina deberia enviar un correo al detectar que el validador esta fallando

    Paso 1.
    
    Para obtener los datos precisos del Pelicano hay que correr CheckDevice() antes del TestConnection, si check device retorna:
    > 301 - Alerta en el validador. Hay algo presente en la bandeja
    > 302 - Alerta en el validador. La puerta de la bandeja esta abierta 
    Se debe correr CleanDevice(), luego si correr TestConnection()

    TestConnection (Estado:Bloqueado / Estado:Falla en comunicacion)
        Mandar el Oink a pantalla de mantenimiento [Solo cambiar de pantalla de mantenimiento a normal desde el dashboard y no reiniciando el Oink]
        Enviar correo al detectar que esta fallando
        Termina flujo del validador
    TestConnection (Estado:OK)
        Continuar al paso 2

    Paso 2.

    Revision anterior a deteccion de monedas (Estado:Falla)
        Cancelar lectura, mandar el Oink a pantalla de mantenimiento y enviar correo al detectar que esta fallando
        Termina flujo del validador
    Revision anterior a deteccion de monedas (Estado:OK)
        Continuar al paso 3

    Paso 3.

    Lectura de monedas (Estado:Falla leve)
        Mostrar mensaje en pantalla dependiendo de la falla
        Tipos de falla:
            Validador lleno [Pelicano]
            Moneda rechazada [Pelicano/Pelicano]
            Billete rechazado [NV10]

    Lectura de monedas (Estado:Falla grave)
        Tipos de falla:
            Validador bloqueado [Pelicano/Pelicano/NV10]
            Compuerta abierta [Pelicano]
            Falla en motor [Pelicano]
        Cancelar lectura, depositar, mandar el Oink a pantalla de mantenimiento y enviar correo al detectar que esta fallando
    Lectura de monedas (Estado:OK)
        Continuar en el ciclo e ir al paso 4 una vez finalizado el deposito

    Paso 4.

    Revisar estado final del validador despues de la operacion (Estado:Falla)
        Mandar el Oink a pantalla de mantenimiento y enviar correo al detectar que esta fallando
    Revisar estado final del dispensador despues de la operacion (Estado:OK)
        Continuar al paso 1
*/