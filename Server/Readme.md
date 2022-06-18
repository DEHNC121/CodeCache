# Database setup
1. Create a PostgreSQL database.
2. Change following fields in the `src/main/resources/hibernate.cfg.xml` file:
```xml
<property name="hibernate.connection.username"></property>
<property name="hibernate.connection.password"></property>
<property name="hibernate.connection.url"></property>
```
