# Krynn-SQL [![][travis img]][travis] [![][license img]][license] [![][discord img]][discord]

## About
Simple java framework like ORM to build sql requests, based on annotations.

## TODO
- Create documentation friendly for new developers.
- Implement cache system.

For the full list check [Projects](https://github.com/Oskarr1239/krynn-sql/projects/3).

## Example
```java
@Table("users")
public class User {
    
    @Column
    @PrimaryKey
    private UUID uuid; 
    
    @Column
    private String name;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
```

```java
KrynnSQL krynnSQL = new KrynnSQL(yourHikariConfig);

Database database = krynnSQL.getDatabase("krynn");
Table<User> table = database.table(User.class);

User user = new User(UUID.randomUUID(), "testuser");

//Insert / Update user
table.update(user);

//Query users
List<User> users = table.query("SELECT * FROM {table}");


```

## License
The Krynn-SQL is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

## Ideas and bugs
If you have any issues or suggestions, please submit them [here](https://github.com/Oskarr1239/krynn-sql/issues).

[travis]: https://travis-ci.org/Oskarr1239/krynn-sql
[travis img]: https://travis-ci.org/Oskarr1239/krynn-sql.svg?branch=master

[license]:https://opensource.org/licenses/Apache-2.0
[license img]:https://img.shields.io/badge/License-Apache%202.0-blue.svg

[discord]: https://discord.gg/RkyqMdF
[discord img]: https://img.shields.io/discord/563074773110882304.svg