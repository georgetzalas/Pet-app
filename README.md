Θέμα εργασίας: Πλατφόρμα υιοθεσίας κατοικιδίων\
Ονομασία εργασίας: Pet-app

Μέλη ομάδας:\
Γιώργος Τζάλας - it2022102,\
Παναγιώτα Σφυρίδη - it2022101,\
Ευαγγελία Μπαλίου - it2022068

Για την υλοποιήση του backend χρησιμοποιήσαμε τα παρακάτω:\
-Java\
-Spring Boot\
-Hibernate ORM\
-PostgreSQL\
-Spring Security\
-Maven\
-Επιπλέον χρησιμοποιήσαμε Email Server, για την αποστολή αυτόματων ενημερωτικών μηνυμάτων προς τους χρήστες του συστήματος, προσθέτοντας στον κώδικα μας το παρακάτω depedency:\
<dependency>\
	groupId>org.springframework.boot</groupId>\
	<artifactId>spring-boot-starter-mail</artifactId>\
</dependency>\
-Το email (της πλατφόρμας) απο το οποίο αποστέλλονται τα μηνύματα είναι το petappkat@gmail.com

Στοιχεία της βάσης μας:\
spring.datasource.username=pet_app_pjhx_user\
spring.datasource.password=djRBCLzWnpuf9eeFBDidf5qz15SYt6CP\
spring.datasource.url=jdbc:postgresql://dpg-ctta428gph6c738h5ucg-a.oregon-postgres.render.com:5432/pet_app_pjhx

# auto generate sql and update db schema at startup\
spring.jpa.generate-ddl=true\
spring.jpa.hibernate.ddl-auto=update

#show and format sql\
spring.jpa.show-sql=true\
spring.jpa.properties.hibernate.format_sql=true

#dialect\
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect

Για την εκτέλεση του προγράμματος ακολουθούμε τα παρακάτω βήματα:
- Εγκατάσταση Java 17 ή νεότερη έκδοση.
- **PostgreSQL** με βάση δεδομένων που ονομάζεται `pet_app_pjhx_user`.
- Ενημέρωση του "application.properties" με τα στοιχεία που δώθηκαν παραπάνω.
- git clone https://github.com/Flektos/Pet-app
- cd Pet-app
- mvn clean install
- mvn spring-boot:run
