# 05. 구성 단위
- 병렬 프로그램을 작성할 때 필요한 동기화 기법과 디자인 패턴에 대해 알아본다.

## 5.1 동기화된 컬렉션 클래스
- 동기화된 컬렉션 클래스: Vector, Hashtable, Collections.synchronizedXxx 메소드
- 동기화된 컬렉션 클래스는 모든 메소드(public)를 캡슐화하여 한 스레드만 사용할 수 있도록 제어하여 스레드 안정성을 확보
- 클라이언트 측 락 기법을 적용할 수 있다.
- 동기화 기법을 내부에 캡슐화하면 동기화 정책을 적용하기 쉽다.

### 5.1.1 동기화된 컬렉션 클래스의 문제점
- 스레드 안정성을 확보하였지만, 여러 스레드가 하나의 컬렉션을 사용할 경우 올바르게 동작하지 않을 수 있다.

### 5.1.2 Iterator와 ConcurrentModificationException
- Iterator는 Collection 클래스의 값을 차례로 반복시키는 표준 방법이다.
- 반복문 실행 도중 다른 스레드가 컬렉션 내부 값을 변경할 때, 즉시 멈춤(Fail-Fast) 형태로 동작
    - 즉시 멈춤(Fail-Fast)
        - 반복문을 실행하는 도중에 컬렉션 클래스 내부 값을 변경하는 상황이 포착되는 즉시 ConcurrentModificationException 예외를 발생시킨다.
        - 변경하는 상황을 포착하기 위하여 변경 횟수를 확인하는 부분이 적절한 동기화가 없기 때문에 스테일 값을 사용할 가능성이 있다. 
- 반복문 실행시 적절한 락을 이용해 동기화 해야 한다.
- 예외적으로 Iterator를 통하지 않고 컬렉션을 직접 수정할경우 단일 스레드 환경에서도 ConcurrentModificationException 예외가 발생할 수 있다.
- 반복문에서 락을 잡고 있는 상황에서 반복문 내부 로직에서 또 다른 락을 확보해야 한다면, 데드락(Deadlock)이 발생할 가능성이 있다.
    - clone 메소드를 통해 특정 스레드에 한정된 복사본을 만들어 복사본을 대상으로 반복문 사용하여 해결이 가능하다.

### 5.1.3 숨겨진 Iterator
- Iterator 사용이 메소드 내부로 숨겨져 있는 경우가 있다.
- 숨겨진 Iterator를 사용하는 모든 메소드에서 ConcurrentModificationException 예외가 발생할 수 있다.

## 5.2 병렬 컬렉션
- 동기화 컬렉션 클래스를 병렬 컬렉션으로 교체하여 성능을 향상 시킬 수 있다.
- 병렬 컬렉션은 여러 스레드에서 동시에 사용할 수 있도록 설계되었다.
    - ConcurrentHashMap: HashMap 클래스의 병렬성 확보 
    - CopyOnWriteArrayList: List 클래스의 반복 열람 연산 성능을 최우선으로 구현한 하위 클래스
    - ConcurrentMap: 없는 경우 추가하는 (Put-If-Absent) 연산, 대치(Replace) 연산, 조건부 제거(Conditional Remove) 연산 등을 추가로 정의한 인터페이스
    - Queue
        - ConcurrentLinkedQueue: 전통적인 FIFO(First-In, First-Out) 큐
        - PriorityQueue: 특정 우선 순위에 따라 큐에 쌓인 항목을 추출하는 큐
        - BlockingQueue: 상황에 따라 대기하는 큐로 프로튜서-컨슈머(Producer-Consumer) 구조에 적합
    - ConcurrentSkipListMap: SortedMap 클래스의 병렬성 확보 (SortedMap: TreeMap을 synchronizedMap으로 동기화한 컬렉션)
    - ConcurrentSkipListSet: SortedSet 클래스의 병렬성 확보 (SortedSet: TreeSet을 synchronizedMap으로 동기화한 컬렉션)

### 5.2.1 ConcurrentHashMap
- 락 스트라이핑(Lock Striping) 동기화 방법을 사용하여 여러 스레드에서 공유할 수 있다. (이전에는 모든 연산이 동일한 락을 사용하여 단일 스레드만 해당 컬렉션을 사용할 수 있었다.)
- 해시 연산은 담고있는 객체들의 hashCode 값이 넓게 분포되어 있지 않으면 훨씬 많은 연산이 필요하다.
- 반복문 작업과 동시에 값이 변경 될 경우 미약한 일관성 전략을 취한다.
    - 미약한 일관성 전략은 반복문과 동시에 값이 변경되어도 Iterator가 만들어진 시점으로 반복을 계속한다.
    - ConcurrentModificationException 예외가 발생하지 않는다.

### 5.2.2 Map 기반의 또 다른 단일 연산
- ConcurrentHashMap은 락을 독점할 수 없어 클라이언트 측 락 기법을 적용할 수 없다.
- 여러 개의 연산을 모아 새로운 단일 연산을 만들고자 할때 ConcurrentHashMap 보다 ConcurrentMap을 사용하여 구현해야 한다.

### 5.2.3 CopyOnWriteArrayList
- List 클래스의 병렬성 확보
- 비슷하게 동작하는 CopyOnWriteArraySet도 있다.
- 변경할 때마다 복사본을 이용하여 연산하기 때문에 동시 사용성에 문제가 발생하지 않는다.
- ConcurrentModificationException 예외가 발생하지 않는다.
- 변경이 잦으면 성능 문제가 발생할 수 있어 변경보다 반복문이 빈번한 경우 사용하기 적절하다.

## 5.3 블로킹 큐와 프로듀서-컨슈머 패턴
- 블로킹 큐는 프로듀서-컨슈머(Producer-Consumer) 패턴을 구현할 때 사용하기 좋다.
- 블로킹 큐를 사용할 경우 처리할 수 있는 양보다 많은 작업량을 처리할 수 있다.
- 블로킹 큐(BlockingQueue) 인터페이스를 제공하며 구현체로는 LinkedBlockingQueue, ArrayBlockingQueue FIFO 큐가 있다.
- PriorityBlockingQueue 클래스는 우선 순위를 기준으로 동작하는 큐로, 정렬이 가능하다. (큐의 항목이 Comparable 이걱나, Comparator 인터페이스를 사용해 정렬)
- SynchronousQueue 클래스는 큐 내부에 값을 갖지 않고 큐를 사용하려는 스레드를 관리한다.
- put, take, offer, poll 메소드 제공
    - put: 값을 추가할 공간이 있을때까지 대기하고 값을 추가
    - take: 가져올 값이 있을때까지 대기하고 값을 가져옴
    - offer: 값을 추가할 공간이 없으면 오류를 발생하고 있으면 값을 추가
    - poll: 가져올 값이 있으면 값을 가져옴
- 프로튜서-컨슈머 패턴
    - 작업을 만드는 주체와 작업을 처리하는 주체를 분리하여 부하를 조절
    
### 5.3.1 예제: 데스크탑 검색
- 프로듀서: 디렉토리 계층 구조를 따라가면서 검색 대항 파일을 큐에 쌓음
- 컨슈머: 대상 파일을 색인
- 프로듀서-컨슈머로 분리하여 코드 가독성이 향상되며 재사용성이 높아진다.
- 두 클래스 간의 작업 흐름은 블로킹 큐가 조절
- 프로듀서-컨슈머 패턴을 사용하는 경우 Executor 프레임윅을 통해 표현 가능
- Executor 프레임웍은 내부적으로 프로듀서-컨슈머 패턴을 사용

### 5.3.2 직렬 스레드 한정
- java.util.concurrent 패키지의 블로킹 큐 클래스는 모두 프로듀서에서 컨슈머로 객체를 넘겨줄 때 동기화 기법이 적용되어 있다.
- 직렬 스레드 한정(Serial Thread Confinement)
    - 객체를 안전하게 공개하면 객체에 대한 소유권을 이전(Transfer)할 수 있다.
    - 소유권이 이전된 객체는 소유권을 갖고 있는 스레드만이 객체의 상태를 알 수 있다.
    - 객체 풀(Object Pool)은 풀에서 소유하고 있는 객체를 외부 스레드에게 빌려주는 역할을 수행한다.
    
### 5.3.3 덱, 작업 가로채기
