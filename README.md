# 쓰레스란?

---
>쓰레드란 프로그램(프로세스) 실행의 단위이며  
하나의 프로세스는 여러개의 쓰레드로 구성이 가능하다.  
하나의 프로세스를 구성하는 쓰레드들은 프로세스에 할당된 메모리, 자원 등을 공유한다.  
프로세스와 같이 실행, 준비, 대기 등의 실행 상태를 가지며   
실행 상태가 변할때마다 쓰레드 문맥교환(context switching)을 수행한다.   
각 쓰레드별로 자신만의 스택과 레지스터를 가진다.  


# 프로세스와 쓰레드의 차이
> 프로세스는 운영체제로부터 자원을 할당받는 작업의 단위이고
쓰레드는 프로세스가 할당받은 자원을 이용하는 실행의 단위이다.
>
+프로세스는 실행 중인 프로그램으로 디스크로부터 메모리에 적재되어 CPU의 할당을 받을 수 있는 것을 말한다.   
하지만 프로세스 생성은 많은 시간과 자원을 소비한다.
쓰레드는 프로세스의 실행 단위라고 할 수 있다.
한 프로세스 내에서 동작되는 여러 실행 흐름으로
프로세스 내의 주소 공간이나 자원을 공유할 수 있다.
이 경우 각각의 쓰레드는 독립적인 작업을 수행해야 하기 때문에 각자의 스택과 PC 레지스터 값을 갖고 있다.


# Thread 구현 기초

---

1. 스레드를 각자 구현해보자 

# [ Thread의 주요 기능 ]
##sleep
  + 현재 쓰레드 잠시 멈출 수있다.
  + 자원에 대한 락을 놓아주지는 않고, 제어권을 넘겨주는 것 
    + 데드락 조심해야 함
##interupt
  + 다른 쓰레드를 깨워서 **InterruptedException** 을 발생시키는 것
  + 예외를 발생시킨 후 종료하거나 다른일을 수행하게 할 수 있다 
##join
  + 해당 쓰레드 작업이 끝날 때 까지 기다리게 하는 것
  + 쓰레드의 순서를 제어할 때 사용할 수 있음

# [Executors]

---

## 정의 
>고수준 (High-Level) Concurrency 프로그래밍   
쓰레드를 만들고 관리하는 작업을 애플리케이션에서 분리해서 관리   
쓰레드 만들기 : 애플리케이션에서 사용될 쓰레드를 만들거나, 쓰레드 풀을 통해 관리   
쓰레드 관리 : 쓰레드의 생명 주기를 관리   
작업 처리 및 실행 : 쓰레드로 작업을 수행하기 위한 API를 제공   


+ Executor.execute(Runnable) or Executor.submit(Runnable)
+ Executor.shutdown()  // 할일을 모두 마치고 종료
+ Executor.shutdownNow()  // 지금 당장 종료
  + ExecutorService
    + Executor 를 상속 받은 인터페이스
    + Runnable과 Callable 모두 실행 가능
    + Executor 를 종료하거나, 여러 Callable을 동시에 실행할 수도 있음

  + ScheduledExecutorService
    + ExecutorService 를 상속받은 인터페이스
    + 특정 시간 이후에 또는 주기적으로 작업을 수행하게 하는 스케줄 기능

# Callable & Future

---

## 정의 
> Runnable과 같은 쓰레드 생성에 사용되는 인터페이스   
> Runnable은 반환(return) 타입이 void   
> Callable은 반환(return) 타입이 객체 


## [ Future ]

### 정의 

- 비동기적인 작업의 현재 상태를 조회하거나 결과를 가져오기 위한 객체
- Runnable / Callable의 상태를 조회하거나 결과를 확인하기 위해 사용 
- 시간이 걸릴 수 있는 작업을 Future 내부에 작성하고,
호출자 스레드가 결과를 기다리는 동안 다른 유용한 작업을 할 수 있음
--> 실행을 맞기고 미래 시점에 결과를 얻는 것으로 이해 가능
- 처리 결과에 대한 콜백을 정의할 수 없어서 이후에 CompletableFuture이 등장함
- .get() 을 통해서 블로킹(Blocking) 콜을 수행
: .get()을 통해서 결과를 확인하기 위해 결과를 기다린다 --> 블로킹(Blocking)


### 주요기능

####get()   
> 블로킹(Blocking) 작업의 처리 결과를 get 하기 위해서 결과를 기다리게 된다(Blocking 상태)

####isDone()   
> 작업이 완료되었으면 true / 아니면 false 반환

#### .cancel()
| Boolean | 파라미터 값 | 결과 값 | 
|:---: | :---:|:---:|
| true |작업 interupt하고 종료| 정상적으로 cancel 수행 O
| false|작업이 끝날 때 까지 대기 후 종료 | 정상적으로 cancel 수행 X

+ cancle 이후에는 get을 통해서 결과를 기다리며 값을 가져올 수 없다.
```java
Exception in thread "main" java.util.concurrent.CancellationException
	at java.util.concurrent.FutureTask.report(FutureTask.java:121)
	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
	at com.seunghoo.thread.future.FutureBasicCancle.main(FutureBasicCancle.java:16)
```


####invokeAll()
> 동시에 실행한 작업 중 제일 오래 걸리는 작업만큼 시간 소요
> 모든 결과가 수행된 뒤 처리되어야 할 때 사용

####invokeAny()
> 여러 작업 중 하나라도 먼저 응답이 오면 끝난다   
동시에 실행한 작업 중 제일 짧게 걸리는 작업 만큼 시간 소요   
같은 일을 여러 쓰레드를 통해 수행한 뒤 먼저 응답이 오면 사실상 나머지는 필요가 X   
블로킹 콜 로 동작   


# CompletableFuture

---

## 정의 
+ java에서 비동기(Asynchronous) 프로그래밍을 가능케하는 인터페이스
+ Future을 통해서 어느정도 가능했지만, 콜백 정의 처럼 제한적인 것들이 있기에 이러한 것들을 극복함
+ 따로 쓰레드풀을 선언하지 않아도 실행이 되며, 쓰레드를 명시적으로 닫아주지 않아도 된다

### Future의 한계
+ 외부에서 Future을 완료시킬 수 없었음 --> 취소, 타임아웃 설정 불가능
+ 항상 블로킹 코드(get()) 뒤에 콜백을 수행해야 했다
  + 블로킹 코드(get())를 사용하지 않고서는 결과의 보장이 되는 상황을 특정할 수 없기 때문
+ 여러 Future를 조합해서 사용할 수 없었다
+ 예외 처리용 API를 제공하지 않았다 

### 주요 인터페이스

| 린터값이 없는경우 | 리턴값이 있는 경우 |
|:---:|:---:|
|runAsync() | supplyAsync() |

| 콜백 제공 |조합하기|예외처리|
|:---:|:---:|:---:|
|thenApply(Function)|thenCompose()|exceptionally(Function)
|thenAccept(Consumer)|thenCombine()|handle(BiFunction)
|thenRun(Runnable)|allOf()
| |anyOf()|

### Callback
어떤 일을 다른 객체에게 맡기고 그일이 끝나서 다시 부를때 까지 나의 일을 하는 것

#### .thenApply(Function)
> 넘겨받은 return값에 대해서 처리를 하고 해당값을 다시 return한다
> 
#### .thenAccept(Consumer)
> Consumer가 인자로 온다. return값을 받아서 처리만 하고 따로 return값은 없다

#### .thenRun(Runnable)
> Runnable이 인자로 온다. return값을 받지도않고 반환하지도 않는다, 추가적인 행동만 한다


### 조합하기


https://velog.io/@neity16/Java-8-5-CompletableFuture
https://velog.io/@dnstlr2933/CompletableFuture-API