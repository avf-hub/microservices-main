### Liquibase DEMO

#### Prerequisite:

Check that in folder ~/data there are no files catalog.*.db.
If there are files, remove it.

#### H2 Console:

During the steps execution, it is recommended to demonstrate the H2 console:
http://127.0.0.1:8081/h2-console

Url: `jdbc:h2:~/data/catalog;AUTO_SERVER=TRUE`
Use `sa/password` as credentials. Demonstrate table `DATABASECHANGELOG`.
Pay attention to the column `MD5SUM`.

#### Steps to reproduce

1) Set active profiles of **CatalogServiceApplication** to `database-per-service,default`
2) Run the **CatalogServiceApplication**. It will apply the changeset to the persistent database.
3) Change some column type in **db.changelog-catalog-create-tables.xml** 
For example, change the type of column `os` from `varchar(128)` to `varchar(255)`
4) Restart app. It should show error

```
[org/springframework/boot/autoconfigure/liquibase/LiquibaseAutoConfiguration$LiquibaseConfiguration.class]: Validation Failed:
     1 changesets check sum
          db/changelog/db.changelog-catalog-create-tables.xml::create-laptops-table::alex-v-yakunin was: 8:98f87d4392657a577f5d6b79a7d1b319 but is now: 8:6680169e901391fdcc7872365a5974dd
```

5) This means that we have changed the changeset, which shouldn't be done.
   Instead, you need to create a new modify set for every change
6) Rollback your changes in file **db.changelog-catalog-create-tables.xml**
7) Create a new file with the description of the desired changes
8) For the name, you have to use some naming convention:
   we are using
   `db.changelog-yyyy-mm-dd-<what was changed>.xml`
9) For example, we created the changeset `db.changelog-2023-11-22-alter-os-column-type.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>

<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
            http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.1.xsd">

    <changeSet id="2023-09-06-alter-os-column-type" author="katunov" context="catalog">
        <modifyDataType tableName="laptops" columnName="os" newDataType="varchar(255)" />
    </changeSet>

</databaseChangeLog>
```

10) Include this changeset to **db.changelog-catalog-service.xml**
11) The place where you include it is very meaningful! This is the example:

```
    <include file="/db/changelog/db.changelog-catalog-create-tables.xml" />
    <include file="/db/changelog/db.changelog-2023-11-22-alter-os-column-type.xml" />
    <include file="/db/changelog/db.changelog-catalog-insert-init-data.xml" />
```

We placed it before data initialization.

12) Run application and show logs
    We should see something like this:

```
2023-09-06T21:45:13.155+02:00  INFO 53269 --- [           main] liquibase.executor                       : Changelog query completed.
Running Changeset: db/changelog/db.changelog-2023-09-06-alter-os-column-type.xml::2023-09-06-alter-os-column-type::katunov
2023-09-06T21:45:13.157+02:00  INFO 53269 --- [           main] liquibase.changelog                      : laptops.os datatype was changed to varchar(255)
2023-09-06T21:45:13.157+02:00  INFO 53269 --- [           main] liquibase.changelog                      : ChangeSet db/changelog/db.changelog-2023-09-06-alter-os-column-type.xml::2023-09-06-alter-os-column-type::katunov ran successfully in 1ms
2023-09-06T21:45:13.158+02:00  INFO 53269 --- [           main] liquibase.executor                       : Changelog query completed.
```
