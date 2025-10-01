# Logging System

A flexible, production-ready logging system for Java applications with support for multiple storage backends and optional AWS SNS notifications.

## 📋 What is it?

This is a **centralized logging system** designed to capture, store, and optionally notify about application logs with different severity levels. The system provides:

- **Thread-safe logging** with automatic metadata capture (timestamp, thread info, stack trace)
- **Multiple storage backends** (File-based and Elasticsearch)
- **Severity-based categorization** (CRITICAL, HIGH, MEDIUM, LOW, WARN, UNDEFINED)
- **Optional AWS SNS notifications** for critical errors with configurable thresholds
- **Asynchronous log processing** using thread pools for better performance
- **Singleton Logger pattern** for application-wide access

## 🎯 Why are we using it?

This logging system addresses several key challenges in application monitoring and debugging:

1. **Centralized Log Management**: Instead of scattered `System.out.println()` statements, provides a unified logging mechanism across the application

2. **Flexible Storage Options**: 
   - Start with simple file-based logging during development
   - Scale to Elasticsearch for production with powerful search and analytics capabilities

3. **Proactive Error Monitoring**: 
   - Automatic notifications via AWS SNS when error thresholds are exceeded
   - Different thresholds for different severity levels (e.g., 5 CRITICAL errors trigger notification vs. 20 LOW errors)

4. **Performance Optimization**:
   - Asynchronous log writing prevents blocking the main application thread
   - Thread pool executor handles concurrent log operations efficiently

5. **Rich Contextual Information**: Automatically captures thread name, thread ID, timestamp, and full stack trace for each log entry

## 🚀 How are we using it?

The system is designed with a simple, intuitive API. See [`App.java`](src/main/java/tech/thedumbdev/App.java) for complete implementation examples.

### Basic Usage Patterns

#### 1. FileStore without Notifications
```java
// Initialize logger with default FileStore (no notifications)
Logger.initLogger(null, null);
Logger instance = Logger.getLogger();

// Add logs with different severity levels
instance.addLog(new Log("Application started successfully", Severity.LOW));
instance.addLog(new Log("Database connection established", Severity.MEDIUM));
instance.addLog(new Log("API rate limit approaching", Severity.HIGH));
instance.addLog(new Log("Out of memory error", Severity.CRITICAL));

// Persist logs to storage
instance.appendLog();

// Cleanup resources
instance.shutdown();
```
*Reference: Lines 19-42 in [`App.java`](src/main/java/tech/thedumbdev/App.java)*

#### 2. FileStore with AWS SNS Notifications
```java
// Configure AWS SNS notification service
Notify notify = new Notify(
    Region.of("us-east-1"),     // AWS Region
    "arn:aws:sns:...",          // SNS Topic ARN
    "admin@example.com",        // Email for notifications
    "ACCESS_KEY",               // AWS Access Key
    "SECRET_KEY"                // AWS Secret Key
);

// Initialize logger with FileStore and notification service
Logger.initLogger(null, notify);
Logger instance = Logger.getLogger();

instance.addLog(new Log("Critical security breach detected", Severity.CRITICAL));
instance.addLog(new Log("Payment processing failed", Severity.HIGH));

instance.appendLog();
instance.shutdown();
```
*Reference: Lines 44-82 in [`App.java`](src/main/java/tech/thedumbdev/App.java)*

#### 3. ElasticStore for Production
```java
// Create Elasticsearch data store
DataStore dataStore = new ElasticStore(
    "http://localhost:9200",    // Elasticsearch URL
    ""                          // API Key (optional)
);

Logger.initLogger(dataStore, null);
Logger instance = Logger.getLogger();

instance.addLog(new Log("User login attempt", Severity.LOW));
instance.addLog(new Log("Database query timeout", Severity.HIGH));

instance.appendLog();
instance.shutdown();
```
*Reference: Lines 84-113 in [`App.java`](src/main/java/tech/thedumbdev/App.java)*

#### 4. ElasticStore with Notifications (Full Production Setup)
```java
// Combine Elasticsearch storage with AWS SNS notifications
Notify notify = new Notify(Region.of(region), topicARN, email, accessKey, secretKey);
DataStore dataStore = new ElasticStore("http://localhost:9200", "");

Logger.initLogger(dataStore, notify);
Logger instance = Logger.getLogger();

instance.addLog(new Log("Application exception occurred", Severity.CRITICAL));
instance.appendLog();
instance.shutdown();
```
*Reference: Lines 115-159 in [`App.java`](src/main/java/tech/thedumbdev/App.java)*

### Notification Thresholds

The system sends AWS SNS notifications based on severity-specific thresholds:

| Severity | Threshold | Description |
|----------|-----------|-------------|
| CRITICAL | 5 errors  | Immediate attention required |
| HIGH     | 10 errors | Significant issues detected |
| MEDIUM   | 15 errors | Moderate concerns |
| LOW      | 20 errors | Minor issues accumulating |
| WARN     | N/A       | No notifications sent |

*Implementation: [`Notify.java`](src/main/java/tech/thedumbdev/service/Notify.java) lines 24-36*

## 🛠️ Technologies Used

### Core Technologies
- **Java 17**: Modern Java with latest LTS features
- **Maven**: Dependency management and build automation

### Key Dependencies
- **Elasticsearch Java Client (9.0.1)**: For Elasticsearch integration and document indexing
  ```xml
  <dependency>
      <groupId>co.elastic.clients</groupId>
      <artifactId>elasticsearch-java</artifactId>
      <version>9.0.1</version>
  </dependency>
  ```

- **AWS SDK for Java - SNS (2.34.5)**: For sending notifications via Simple Notification Service
  ```xml
  <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>sns</artifactId>
      <version>2.34.5</version>
  </dependency>
  ```

- **SLF4J NOP (2.0.9)**: Silent logger implementation to suppress Elasticsearch client logs
  ```xml
  <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-nop</artifactId>
      <version>2.0.9</version>
  </dependency>
  ```

- **JUnit Jupiter (5.11.0)**: For unit and parameterized testing

### Infrastructure
- **Docker Compose**: For local Elasticsearch deployment
- **Elasticsearch 9.1.3**: Search and analytics engine (containerized)

## 📦 Supported Stores

The system implements a flexible `DataStore` interface, allowing easy integration of different storage backends:

### 1. **FileStore** (Default)
- **Location**: `~/logs/` directory in user's home folder
- **File Format**: Binary serialized objects with `.log` extension
- **Naming Convention**: `{timestamp}.log` (e.g., `1696118400.log`)
- **Use Case**: Development, debugging, and lightweight logging scenarios
- **Features**:
  - Automatic directory creation
  - Thread-safe append operations
  - Serialized Java objects for easy deserialization

**Implementation**: [`FileStore.java`](src/main/java/tech/thedumbdev/data/FileStore.java)

### 2. **ElasticStore**
- **Connection**: Configurable Elasticsearch URL (default: `http://localhost:9200`)
- **Index Strategy**: Creates new index per logger instance using timestamp
- **Document Format**: JSON representation of Log objects
- **Use Case**: Production environments requiring search, analytics, and log aggregation
- **Features**:
  - Automatic index creation
  - Structured JSON documents
  - Powerful search and filtering capabilities
  - Integration with Kibana for visualization

**Implementation**: [`ElasticStore.java`](src/main/java/tech/thedumbdev/data/ElasticStore.java)

### Adding Custom Stores

To add a new storage backend, implement the `DataStore` interface:

```java
public interface DataStore {
    void appendLog(Set<Log> logs) throws Exception;
    void close() throws Exception;
}
```

Examples: MongoDB, PostgreSQL, Cloud Storage (S3, GCS), Redis, etc.

## 🐳 Local Development Setup

Start Elasticsearch locally using Docker:

```bash
docker-compose up -d
```

This starts Elasticsearch on `http://localhost:9200` with:
- Single-node mode
- Security disabled (development only)
- 1GB heap size

Access Elasticsearch:
```bash
curl http://localhost:9200
```

## 🛠️ Project Structure
```
src/main/java/tech/thedumbdev/
├── App.java                    # Example implementations and usage
├── data/
│   ├── DataStore.java         # Storage interface
│   ├── FileStore.java         # File-based storage implementation
│   ├── ElasticStore.java      # Elasticsearch storage implementation
│   └── exceptions/            # Custom exceptions
├── enums/
│   └── Severity.java          # Log severity levels
├── pojo/
│   └── Log.java               # Log data model
└── service/
    ├── Logger.java            # Core logging service (Singleton)
    └── Notify.java            # AWS SNS notification service
```


## 🔧 Building and Running

### Build the project
```bash
mvn clean package
```

### Run the application
```bash
java -cp target/logging-system-1.0-SNAPSHOT.jar tech.thedumbdev.App
```

### Run tests
```bash
mvn test
```

## 📝 License

This project is available for educational and personal use.

---

**Note**: The examples in [`App.java`](src/main/java/tech/thedumbdev/App.java) are intentionally commented out. Uncomment the desired example block and replace placeholder AWS credentials with real values before running.