package com.valdeolivar.practica1livh

fun main(){
    operaNumeros(52,26)
    operaNumeros(30,18)

    val miLambdaSuma: (Int, Int) -> Unit = {a,b ->
        println("La suma de $a + $b es ${a+b}")
    }

    operaNumeros(50,20,miLambdaSuma)
}

fun operaNumeros(num1: Int, num2: Int){
    println("La suma de $num1 + $num2 es: ${num1+num2}")
}

fun operaNumeros(num1: Int, num2: Int, operacion: (Int, Int) -> Unit){
    //println("La suma de $num1 + $num2 es: ${num1+num2}")
    /*val number1 = num1 +1
    val number2 = num2 +1*/
    operacion(num1,num2)
}