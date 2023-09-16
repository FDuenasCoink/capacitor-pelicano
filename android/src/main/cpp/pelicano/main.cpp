/**
 * @file main.cpp
 * @author Oscar Pineda (o.pineda@coink.com)
 * @brief Archivo principal basico que llama a las funciones de PelicanoControl
 * @version 1.1
 * @date 2023-05-23
 * 
 * @copyright Copyright (c) 2023
 * 
 */

//Complacion: g++ -o main main.cpp PelicanoControl.cpp StateMachine.cpp ValidatorPelicano.cpp -I/home/coink/oink/piggybank/connect/coin/Pelicano/spdlog/include/

#include <stdio.h>
#include <stdbool.h>
#include <iostream>

#include "PelicanoControl.hpp"

using namespace PelicanoControl;

int main() {

    printf("main() called.\r\n");

    PelicanoControlClass PelicanoControlObject;

    Response_t Respuesta;
    bool FlagContinue = false;

    int Total = 0;

    //Variables externas parametrizadas [Se deben asignar antes de iniciar el log y de conectar]
    PelicanoControlObject.WarnToCritical = 10;
    PelicanoControlObject.MaxCritical = 4;
    PelicanoControlObject.Path = "logs/Pelicano.log";
    PelicanoControlObject.LogLvl = 1;
    PelicanoControlObject.MaximumPorts = 10;
    //PelicanoControlObject.InsertedCoins [Variable de solo lectura]

    PelicanoControlObject.InitLog();
    
    Respuesta = PelicanoControlObject.Connect();
    std::cout<<"Connect retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;

    //No es necesario correr GetInsertedCoins, (Solo se creo para la pantalla de mantenimiento)
    Respuesta = PelicanoControlObject.GetInsertedCoins();
    std::cout<<"GetInsertedCoins retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<" Y "<<PelicanoControlObject.InsertedCoins<<std::endl;

    float VidaUtil = 100.0 - ((PelicanoControlObject.InsertedCoins*100.0) / 750000.0);
    std::cout<<"La vida util restante del Pelicano es: "<<VidaUtil<<" %"<<std::endl;

    Respuesta = PelicanoControlObject.CheckDevice();
    std::cout<<"CheckDevice retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;

    if ((Respuesta.StatusCode == 200) | (Respuesta.StatusCode == 301) | (Respuesta.StatusCode == 302)){
        FlagContinue = true;
    }
    
    if (FlagContinue){

        Respuesta = PelicanoControlObject.StartReader();
        std::cout<<"StartReader retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;

        int i=0;
        CoinError_t RespuestaCE;
        while(i<400){

            RespuestaCE = PelicanoControlObject.GetCoin();
            if (RespuestaCE.StatusCode != 303){
                std::cout<<"GetCoin retorna. StatusCode: "<<RespuestaCE.StatusCode<<" Event: "<<RespuestaCE.Event<<" Coin: "<<RespuestaCE.Coin<<" Message: "<<RespuestaCE.Message<<" Remaining: "<<RespuestaCE.Remaining<<std::endl;    
                if (RespuestaCE.Remaining > 1){
                    CoinLost_t RespuestaLC;
                    RespuestaLC = PelicanoControlObject.GetLostCoins();
                    std::cout<<"GetLostCoins retorna. 50: "<<RespuestaLC.CoinCinc<<" 100: "<<RespuestaLC.CoinCien<<" 200: "<<RespuestaLC.CoinDosc<<" 500: "<<RespuestaLC.CoinQuin<<" 1000: "<<RespuestaLC.CoinMil<<std::endl;    
                    Total = Total + (RespuestaLC.CoinCinc * 50) + (RespuestaLC.CoinCien * 100) + (RespuestaLC.CoinDosc * 200) + (RespuestaLC.CoinQuin * 500) + (RespuestaLC.CoinMil * 1000);
                }
                if ((RespuestaCE.StatusCode == 402) |(RespuestaCE.StatusCode == 403)){
                    i = 400;
                }
                Total = Total + RespuestaCE.Coin;
            }
            if (i == 200){
                std::cout<<"Quitando ambas monedas de 500....."<<std::endl;
                Respuesta = PelicanoControlObject.ModifyChannels(191,247);
                std::cout<<"Modify channels retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;
            }
            i++;
        }

        Respuesta = PelicanoControlObject.CheckDevice();
        std::cout<<"CheckDevice retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;
        
        Respuesta = PelicanoControlObject.StopReader();
        std::cout<<"StopReader retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;

        //No es necesario correr Reset Device, esta incluido en startreader y stopreader (Solo se creo para la pantalla de mantenimiento)
        Respuesta = PelicanoControlObject.ResetDevice();
        std::cout<<"ResetDevice retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;

        //No es necesario correr Clean Device, esta incluido en stopreader (Solo se creo para la pantalla de mantenimiento)
        Respuesta = PelicanoControlObject.CleanDevice();
        std::cout<<"CleanDevice retorna: "<<Respuesta.StatusCode<<" Y "<<Respuesta.Message<<std::endl;

        std::cout<<"Total depositado: "<<Total<<" COP "<<std::endl;

        TestStatus_t Status = PelicanoControlObject.TestStatus();
        std::cout<<"TestStatus retorna. Version: "<<Status.Version<<" Device: "<<Status.Device<<std::endl;
        std::cout<<"TestStatus retorna. ErrorType: "<<Status.ErrorType<<" ErrorCode: "<<Status.ErrorCode<<std::endl;
        std::cout<<"TestStatus retorna. Message: "<<Status.Message<<" AditionalInfo: "<<Status.AditionalInfo<<std::endl;
        std::cout<<"TestStatus retorna. Priority: "<<Status.Priority<<std::endl;
    }
    
    return 0;
}