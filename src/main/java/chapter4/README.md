# 04. 객체 구성
## 4.1 스레드 안전한 클래스 설계
- 변수의 타입, 값의 종류, 범위, 동기화 정책을 고려하여 설계
- 동기화 정책: 상태 값에 대한 스레드 안정성을 확보하기 위해 사용하는 것을 말하며, `객체의 불변성`, `스레드 한정`, `락` 등을 사용할 수 있다.

### 4.1.1 동기화 요구사항 정리
- 클래스의 상태 범위와 변동 폭을 인지하여 스레드 안정성을 확보한다.
- 클래스의 상태에 대한 제약 조건에 따라서 다른 동기화 기법, 캡슐화 방법을 사용한다.
- 클래스의 상태를 올바르게 유지하려면, 상태 변수를 클래스 내부로 캡슐화해야 한다.
- 클래스의 상태가 여러 변수에 의해 정의된다면, 여러 변수의 연산을 단일 연산으로 구현해야 한다.
- 상태 범위: 상태 값의 범위를 말하며, 범위가 좁을 수록 객체 상태를 파악하기 쉽다.
- 스레드 안정성: 여러 스레드가 동시에 클래스를 사용할 때 클래스의 상태 값을 안정적으로 유지하는 경우 스레드 안정성을 확보했다고 할 수 있다.

### 4.1.2 상태 의존 연산
- 상태를 기반으로 하는 클래스의 상태 조건에 따른 연산을 상태 의존 연산(State Dependent)이라고 한다.
- 상태 조건을 만족할 때까지 대기하여 연산을 수행하는 방법
    - wait & notify
    - 라이브러리 활용 (세마포어, 블로킹 큐)

### 4.1.3 상태 소유권
- 객체의 상태는 해당 객체에 포함되는 모든 객체와 변수가 가질 수 있는 전체 상태의 부분 집합이다.
- 객체의 상태는 해당 객체가 실제로 소유하는 데이터만을 기준으로 한다.
- 변수를 공개하면 해당 변수에 대한 통제권을 잃는다.
- 객체를 공개하면 해당 객체에 대한 소유권을 공유한다.
- 일반적으로 넘겨받은 객체에 대한 소유권을 갖지 않는게 일반적이지만, 넘겨 받은 객체가 소유권을 확보하도록 메소드를 제공한다면 소유권을 확보할 수 있다.

## 4.2 인스턴스 한정
- 객체를 캡슐화하면 스레드 안정성을 확보할 수 있는데 이런 경우를 `인스턴스 한정`기법을 사용한다고 볼 수 있다.
- 인스턴스 한정 기법을 사용하면 스레드 안정성을 확보하는지 쉽게 분석이 가능하여 좀 더 안전한 객체를 구현할 수 있다.
- 한정 기법
    - 인스턴스 한정: private 변수
    - 블록 한정: 블록 내부 로컬 변수 
    - 스레드 한정: 다른 스레드로 넘겨주지 않는 객체 
- 스레드 안전하지 않은 클래스에 대해 스레드 안정성을 확보하기 위한 클래스도 존재한다.
    - Collections.synchronizedList: 데코레이터 패턴(Decorator Pattern)을 활용한 팩토리 메소드

### 4.2.1. 자바 모니터 패턴
- 모니터 패턴을 따르는 객체는 변경가능한 데이터를 캡슐화하고 암묵적인 락으로 데이터에 대한 동시 접근을 막는다.
- 암묵적인 락은 외부에 공개되어 있기 때문에 암묵적인 락을 사용하기 보다 private 객체를 사용하여 락으로 활용하는 것이 좋다.
- 모니터 락(Monitor Lock): 자바 언어에 내장된 암묵적인 락을 말한다.

### 4.2.2 예제: 차량 위치 추적
- 변경 가능한 데이터를 외부에 넘겨줄때 복사본을 넘겨주면 부분적이나마 스레드 안전성을 확보할 수 있다.
- 스레드 개수가 많아지면 복사본을 넘겨주는 경우 성능 문제가 발생할 수 있다.

## 4.3 스레드 안정성 위임
- 클래스의 상태 변수가 스레드 안전하다면, 클래스가 스레드 안정성 문제를 해당 변수에 위임(Delegate)했다고 한다.

### 4.3.1 예제: 위임 기법을 활용한 차량 추적
- 객체 상태를 모두 불변 상태로 만들어 스레드 안정성을 위임 한다.
- 객체의 상태를 공유할때 고정된 데이터를 넘겨주거나, 동적인 데이터를 넘겨줄 수 있다.

### 4.3.2 독립 상태 변수
- 객체 내부 변수가 두 개 이상일 경우 변수가 서로 독립적이라면 스레드 안정성을 위임할 수 있다.

### 4.3.3 위임할 때의 문제점
- 객체 내부 변수가 서로 의존성을 갖을 경우 변수를 사용할 때 단일 연산으로 처리하도록 동기화 기법을 적용해야 한다.
- 객체 내부 번수가 서로 의존성이 없는 경우 volatile로 선언해도 스레드 안정성을 보장한다.

### 4.3.4 내부 상태 변수를 외부에 공개
- 상태 변수가 스레드 안전하고, 의존성이 없으며, 잘 못된 상태에 빠질 가능성이 없는 경우에만 변수를 외부에 공개해야 한다.

### 4.3.5 예제: 차량 추적 프로그램의 상태를 외부에 공개
- 스레드 안정성을 해치지 않으면서 클래스 내부 변경이 가능한 값을 외부에 공개한다.

## 4.4 스레드 안전하게 구현된 클래스에 기능 추가
- 기존 클래스에 직접 기능 추가
- 기존 클래스를 상속받아 기능 추가: 상속을 이용하여 기능을 추가할 경우 동기화를 맞춰야 할 대상이 두 개 이상의 클래스에 분산되어 직접 추가하는 것 보다 위험하다.

### 4.4.1 호출하는 측의 동기화
- 도우미 클래스를 구현하여 기능 추가
- 도우미 클래스를 통해 동기화할 경우 클라이언트 측 락(Client-Size Lock)이나 외부 락(External Lock)을 사용하여 동일한 락을 사용하도록 해야한다.
- 도우미 클래스를 통해 클라이언트 측 락 방법으롣 단일 연산을 구현할 경우 특정 클래스 내부에서 사용하는 락을 전혀 관계없는 제3의 클래스에서 갖다 쓰기 때문에 위험하다.

### 4.4.2 클래스 제구성
- 재구성(Composition)하여 기능 추가: 모니터 패턴을 활용해 새로운 클래스 내부로 캡슐화
- 도우미 클래스 구현 기법보다 안전하다.

## 4.5 동기화 정책 문서화하기 
- 클래스의 동기화 정책에 대해서 문서화해야 한다.
    - 어느 수준까지 스레드 안전성을 보장하는 가
    - 어떤 동기화 기법이나 정책이 적용되었는가
- JDK 1.5+ 부터 사용가능한 @GuardedBy 어노테이션을 사용한다.

### 4.5.1 애매한 문서 읽어내기
- 자바 문서의 대부분은 스레드 안정성에 대한 요구사항이나 보장 범위에 대한 언급이 별로 없다.
- 개발자의 논리적 추측에 의해 스레드 안정성을 확보해야 하는 경우가 많다.
    
   