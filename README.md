# Wallet Service

## Package structure
```
.
├── README.md
├── api
│   ├── build.gradle.kts
│   ├── gradle
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── src
│       ├── main
│       │   ├── kotlin
│       │   │   └── me
│       │   │       └── golf
│       │   │           └── api
│       │   └── resources
│       │       ├── application.yml
├── app
│   ├── build.gradle.kts
│   ├── gradle
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── src
│       ├── main
│       │   ├── kotlin
│       │   │   └── me
│       │   │       └── golf
│       │   │           ├── AppApplication.kt
│       │   │           └── app
│       │   └── resources
│       │       ├── application.properties
├── build.gradle.kts
├── core
│   ├── build.gradle.kts
│   ├── gradle
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── src
│       ├── main
│       │   ├── kotlin
│       │   │   └── me
│       │   │       └── golf
│       │   │           └── core
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── kotlin
│               └── me
│                   └── golf
│                       └── core
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── infra
│   ├── build.gradle.kts
│   ├── gradle
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle.kts
│   └── src
│       ├── main
│       │   ├── kotlin
│       │   │   └── me
│       │   │       └── golf
│       │   │           └── infra
│       │   └── resources
│       │       ├── application.properties
└── settings.gradle.kts
```

## 요구사항
- 정산 정보를 기록합니다.
- 정산 서비스는 사용자가 결제 완료 시 실제 판매자와 정산을 하기 위한 기록을 남기는 역할을 합니다.

## 목표
- Controller에 추상화 된 이벤트 리스너를 통해 Commerce Service로부터 이벤트를 수신합니다. 구현체는 인프라에 구현되어야 합니다.
- Commerce Service와는 다르게 도메인 모델을 JPA entity로 사용합니다.
