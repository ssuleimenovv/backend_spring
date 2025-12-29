# 📊 ERD ДИАГРАММА - ПОШАГОВОЕ СОЗДАНИЕ

## 🎯 ЧТО РИСОВАТЬ

Entity-Relationship Diagram (ERD) показывает структуру базы данных и связи между таблицами.

---

## 🛠 ИНСТРУМЕНТ

Используйте: **https://app.diagrams.net/** (draw.io)

---

## 📐 ШАГ ЗА ШАГОМ

### Шаг 1: Откройте draw.io

1. Перейдите на https://app.diagrams.net/
2. **Device:** выберите "Save diagrams to: Device"
3. **Create New Diagram**
4. **Blank Diagram** → Create

---

### Шаг 2: Создайте первую таблицу (students)

1. **Слева в панели** найдите **Entity Relation** раздел
2. Перетащите **Table** на канвас
3. **Два раза кликните** на таблицу → появится редактор

#### Заполните таблицу students:

```
Заголовок таблицы: STUDENTS

Поля (каждое поле - отдельная строка):
┌─────────────────────────────────┐
│ PK  id              BIGSERIAL   │  ← Primary Key
│     name            VARCHAR(100)│
│ UQ  email           VARCHAR(100)│  ← Unique
│ UQ  student_id      VARCHAR(20) │  ← Unique
│     created_at      TIMESTAMP   │
│     updated_at      TIMESTAMP   │
└─────────────────────────────────┘
```

**Как добавлять строки:**
- Нажмите **+** внизу редактора чтобы добавить строку
- Для каждого поля введите: `название типданных`

**Обозначения:**
- **PK** = Primary Key (жирный шрифт или выделите цветом)
- **UQ** = Unique (можно подчеркнуть)

**Цвета:**
- Заголовок: **синий** (#4472C4)
- Primary Key строка: **светло-желтый** (#FFF2CC)

---

### Шаг 3: Создайте вторую таблицу (courses)

1. Перетащите еще одну **Table** правее
2. Два раза кликните → редактор

#### Заполните таблицу courses:

```
Заголовок таблицы: COURSES

Поля:
┌─────────────────────────────────┐
│ PK  id              BIGSERIAL   │  ← Primary Key
│     name            VARCHAR(200)│
│ UQ  code            VARCHAR(20) │  ← Unique
│     capacity        INTEGER     │
│     enrolled        INTEGER     │
│     instructor      VARCHAR(100)│
│     created_at      TIMESTAMP   │
│     updated_at      TIMESTAMP   │
└─────────────────────────────────┘
```

---

### Шаг 4: Создайте третью таблицу (enrollments)

1. Перетащите третью **Table** между первыми двумя (по центру снизу)
2. Два раза кликните → редактор

#### Заполните таблицу enrollments:

```
Заголовок таблицы: ENROLLMENTS

Поля:
┌─────────────────────────────────┐
│ PK  id              BIGSERIAL   │  ← Primary Key
│ FK  student_id      BIGINT      │  ← Foreign Key
│ FK  course_id       BIGINT      │  ← Foreign Key
│     status          VARCHAR(20) │
│     enrolled_at     TIMESTAMP   │
│                                 │
│ UNIQUE (student_id, course_id)  │  ← Constraint
└─────────────────────────────────┘
```

**Foreign Keys строки:**
- Цвет: **светло-зеленый** (#D9EAD3)

---

### Шаг 5: Нарисуйте связи (Relationships)

#### Связь 1: students → enrollments (One-to-Many)

1. **На панели слева** найдите **Entity Relation** → **Relation** стрелку
2. **Перетащите стрелку** или выберите и кликните:
   - **От:** `students.id` (Primary Key)
   - **К:** `enrollments.student_id` (Foreign Key)

3. **Настройте стрелку:**
   - **Начало (students сторона):** "1" или кружок (One)
   - **Конец (enrollments сторона):** "crow's foot" или "*" (Many)

4. **Подпишите связь:**
   - Два раза кликните на линию
   - Добавьте текст: `"1" на students стороне`
   - `"*" или "N" на enrollments стороне`

#### Связь 2: courses → enrollments (One-to-Many)

1. **Перетащите стрелку:**
   - **От:** `courses.id`
   - **К:** `enrollments.course_id`

2. **Настройте:**
   - **Начало (courses):** "1"
   - **Конец (enrollments):** "*" или "N"

---

### Шаг 6: Добавьте легенду (Legend)

1. **Вставьте текстовый блок** в угол:

```
┌─────────────────────────────┐
│         LEGEND              │
├─────────────────────────────┤
│ PK = Primary Key            │
│ FK = Foreign Key            │
│ UQ = Unique Constraint      │
│ 1  = One                    │
│ *  = Many                   │
│                             │
│ Relationships:              │
│ • One Student → Many        │
│   Enrollments               │
│ • One Course → Many         │
│   Enrollments               │
│ • Student ↔ Course =        │
│   Many-to-Many through      │
│   Enrollments               │
└─────────────────────────────┘
```

---

### Шаг 7: Добавьте примеры данных (опционально)

Можно добавить текстовые блоки под каждой таблицей:

**Под students:**
```
Examples:
• Alice Johnson (STU001)
• Bob Smith (STU002)
• Charlie Brown (STU003)
```

**Под courses:**
```
Examples:
• CS101: Intro to Computer Science
• CS201: Data Structures
• CS301: Database Systems
```

**Под enrollments:**
```
Examples:
• Student 1 → Course 1 (ACTIVE)
• Student 2 → Course 2 (ACTIVE)
```

---

## 🎨 ФИНАЛЬНОЕ ОФОРМЛЕНИЕ

### Расположение таблиц:

```
        ┌─────────────┐              ┌─────────────┐
        │  STUDENTS   │              │   COURSES   │
        │             │              │             │
        │ PK id       │              │ PK id       │
        │    name     │              │    name     │
        │ UQ email    │              │ UQ code     │
        │ ...         │              │ ...         │
        └──────┬──────┘              └──────┬──────┘
               │                             │
               │ 1                           │ 1
               │                             │
               │        ┌─────────────┐      │
               └────────┤ ENROLLMENTS ├──────┘
                      * │             │ *
                        │ PK id       │
                        │ FK student_id
                        │ FK course_id│
                        │    status   │
                        │ ...         │
                        └─────────────┘
```

### Цветовая схема:

- **students:** Синий заголовок (#4472C4)
- **courses:** Зеленый заголовок (#6AA84F)
- **enrollments:** Оранжевый заголовок (#E69138)
- **Primary Keys:** Светло-желтый фон (#FFF2CC)
- **Foreign Keys:** Светло-зеленый фон (#D9EAD3)
- **Связи:** Черные линии

---

## 💾 СОХРАНЕНИЕ

1. **File → Export as → PNG**
2. **Настройки:**
   - Zoom: 100%
   - Border Width: 10
   - Selection Only: OFF (весь канвас)
3. **Export**
4. **Сохраните как:** `database-erd.png`
5. **Скопируйте в:** `course-registration/docs/diagrams/`

---

## ✅ ПРОВЕРКА

Хорошая ERD должна показывать:
- [x] Все 3 таблицы
- [x] Все поля с типами данных
- [x] Primary Keys помечены (PK)
- [x] Foreign Keys помечены (FK)
- [x] Unique constraints помечены (UQ)
- [x] Связи показаны стрелками
- [x] Мультипликаторы (1, *, N)
- [x] Легенда (Legend)
- [x] Читаемый шрифт
- [x] Профессиональные цвета

---

## 📝 КАКОЙ КОД ИЗ КАКОГО МИКРОСЕРВИСА

### Вся база данных - ТОЛЬКО в Microservice 1

**course-registration/src/main/resources/db/migration/**

#### V1__Create_students_table.sql
```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### V2__Create_courses_table.sql
```sql
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    capacity INTEGER NOT NULL,
    enrolled INTEGER DEFAULT 0,
    instructor VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### V3__Create_enrollments_table.sql
```sql
CREATE TABLE enrollments (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    UNIQUE (student_id, course_id)
);
```

#### V4__Insert_test_data.sql
```sql
-- Students
INSERT INTO students (name, email, student_id) VALUES
    ('Alice Johnson', 'alice.johnson@university.com', 'STU001'),
    ('Bob Smith', 'bob.smith@university.com', 'STU002'),
    ('Charlie Brown', 'charlie.brown@university.com', 'STU003');

-- Courses
INSERT INTO courses (name, code, capacity, enrolled, instructor) VALUES
    ('Introduction to Computer Science', 'CS101', 30, 0, 'Dr. Smith'),
    ('Data Structures and Algorithms', 'CS201', 25, 0, 'Dr. Johnson'),
    ('Database Systems', 'CS301', 20, 0, 'Dr. Williams');
```

### JPA Entities - ТОЛЬКО в Microservice 1

**course-registration/src/main/java/.../entity/**

#### Student.java
```java
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String studentId;
    // ...
}
```

#### Course.java
```java
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String code;
    private Integer capacity;
    // ...
}
```

#### Enrollment.java
```java
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    private String status;
    // ...
}
```

---

## 🎯 ВАЖНЫЕ ДЕТАЛИ ДЛЯ ERD

### Связи:

**One-to-Many: students → enrollments**
- Один студент может иметь много записей на курсы
- Реализация: `student_id` в таблице `enrollments` (FK)

**One-to-Many: courses → enrollments**
- Один курс может иметь много студентов
- Реализация: `course_id` в таблице `enrollments` (FK)

**Many-to-Many: students ↔ courses**
- Много студентов могут быть на много курсах
- Реализация: через промежуточную таблицу `enrollments`

### Constraints:

1. **Primary Keys:** Уникальный идентификатор каждой записи
2. **Foreign Keys:** Обеспечивают целостность данных
3. **Unique:** `email`, `student_id`, `code` - не могут повторяться
4. **Composite Unique:** `(student_id, course_id)` - студент не может записаться на один курс дважды

---

## 🖼️ ПРИМЕР ФИНАЛЬНОЙ ERD

```
┌─────────────────────────────────┐
│         STUDENTS                │ (синий заголовок)
├─────────────────────────────────┤
│ 🔑 PK  id            BIGSERIAL  │ (желтый фон)
│        name          VARCHAR    │
│    UQ  email         VARCHAR    │
│    UQ  student_id    VARCHAR    │
│        created_at    TIMESTAMP  │
│        updated_at    TIMESTAMP  │
└──────────────┬──────────────────┘
               │
               │ 1 (One)
               │
               ▼
┌──────────────────────────────────┐
│       ENROLLMENTS                │ (оранжевый заголовок)
├──────────────────────────────────┤
│ 🔑 PK  id            BIGSERIAL   │ (желтый фон)
│ 🔗 FK  student_id    BIGINT      │ (зеленый фон)
│ 🔗 FK  course_id     BIGINT      │ (зеленый фон)
│        status        VARCHAR(20) │
│        enrolled_at   TIMESTAMP   │
│                                  │
│ UNIQUE (student_id, course_id)   │
└──────────────┬───────────────────┘
               │
               │ * (Many)
               │
               ▼
┌─────────────────────────────────┐
│         COURSES                 │ (зеленый заголовок)
├─────────────────────────────────┤
│ 🔑 PK  id            BIGSERIAL  │ (желтый фон)
│        name          VARCHAR    │
│    UQ  code          VARCHAR    │
│        capacity      INTEGER    │
│        enrolled      INTEGER    │
│        instructor    VARCHAR    │
│        created_at    TIMESTAMP  │
│        updated_at    TIMESTAMP  │
└─────────────────────────────────┘
```

---

## ⏱️ ВРЕМЯ

**15-20 минут** если следуете инструкциям.

---

## 🎓 НА ЗАЩИТЕ

**Покажите ERD и скажите:**

> "Вот структура базы данных. У нас три таблицы: students, courses, и enrollments. Enrollments - это junction table для many-to-many связи между студентами и курсами. Есть foreign keys для целостности данных и unique constraints для бизнес-правил - например, студент не может записаться на один курс дважды."

---

**УДАЧИ!** 🚀
