package com.reportalo.tpFinal.config;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class CargaInicialUsuario {

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Vas a necesitar acceder a las propiedades de application.propertis (admin.username y admin.password). Para eso, usá @Value
    //Esto le dice a Spring: “Buscá en las propiedades (o en variables de entorno) los valores de admin.username y admin.password y ponelos acá”.


    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;


    @PostConstruct
    public void crearUsuarios() {
        if (!userDetailsManager.userExists(adminUsername)) {
            UserDetails admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles("ADMIN")
                    .build();
            userDetailsManager.createUser(admin);
            System.out.println("ADMIN creado - Usuario: " + adminUsername + ", Clave: " + adminPassword); //ESTO SOLO EN DESARROLLO X DIOS EN PRODUCCION NO
        }
    }


}


//PostConstruct es una anotación de Java (no solo de Spring) que marca un método para que se ejecute automáticamente justo después de que el bean haya sido creado e inyectado, es decir, después del constructor y después de que se resolvieron las dependencias (@Autowired).
/*
* Un bean es simplemente un objeto que Spring crea, configura y administra por vos.

✅ Ejemplo concreto:
Cuando hacés esto:

java
Copiar
Editar
@Component
public class CargaInicialDeUsuarios {
    ...
}
Estás diciendo:
🗣️ “¡Spring! Esta clase va a ser un bean. Encargate vos de crearla y de inyectarle las dependencias (@Autowired).”

Y Spring responde:
🤖 “Dale, ya me encargo”.

Entonces…
El objeto de tipo CargaInicialDeUsuarios es un bean.

Spring lo crea automáticamente cuando arranca la app.

Spring le pone todo lo que necesita (por ejemplo JdbcUserDetailsManager, PasswordEncoder, etc.).

Luego, llama al método con @PostConstruct.

*
*
*
* Bueni, ahora analicemos esto: UserDetails admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles("ADMIN")
                    .build();
            userDetailsManager.createUser(admin); User de donde salio? .builder() de donde viene? .username .password . roles  que son? atributos de una clase o de donde vienen? son predeterminados? los ejegiste vos?


ChatGPT dijo:
¡Excelente análisis! Vamos punto por punto porque es clave que entiendas de dónde viene todo eso. Lo que estás viendo es una construcción de un objeto UserDetails usando un builder, que es un patrón muy común en Java para crear objetos de forma clara y fluida.

🔹 ¿Qué es User?
User es una clase que viene de Spring Security.

import org.springframework.security.core.userdetails.User;
Es una implementación concreta de la interfaz UserDetails, que representa a un usuario del sistema de autenticación de Spring Security.

🔹 ¿Qué hace .builder()?
El método User.builder() devuelve un builder, o sea, un objeto que te permite configurar paso a paso un User antes de construirlo. Esto es parte del Builder Pattern.

👉 Sirve para crear objetos inmutables sin tener un constructor larguísimo.

🔹 ¿Y .username(), .password(), .roles()?
Todos esos son métodos del builder que:

.username(String) → establece el nombre de usuario.

.password(String) → establece la contraseña (¡ya encriptada!).

.roles(String...) → asigna uno o más roles al usuario (como "ADMIN", "USER").

🧠 No son atributos directamente accesibles ni algo que inventó tu profe: están definidos en el builder de la clase User de Spring Security.

🔧 ¿Y build()?
Ese método finaliza el proceso de construcción y devuelve el objeto UserDetails listo para usarse.

*
*
*
* */