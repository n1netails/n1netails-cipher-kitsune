# N1netails

<div align="center">
  <img src="https://github.com/n1netails/n1netails-cipher-kitsune/blob/v0.0.5/kitsune-v0.0.5.jpeg" alt="N1netails" width="500" style="display: block; margin: auto;"/>
</div>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Cipher Kitsune — N1netails Encryption Cipher Utilities

Cipher Kitsune is an encryption utility related to n1netails.
Supported Features:

## Key Rotation Services

The following key rotation services are available:

- `AES_TO_AES` - Rotates data from an old AES key to a new AES key.
- `DES_TO_AES` - Rotates data from an old DES key to a new AES key.
- `AES_CTR` - Rotates data from an old AES-CTR key to a new AES-CTR key.
- `CHACHA20` - Rotates data from an old ChaCha20 key to a new ChaCha20 key.
- `TWOFISH` - Rotates data from an old Twofish key to a new Twofish key.
- `DES_EDE_TO_AES` - Rotates data from an old 3DES (DESede) key to a new AES key.

## Key Generator Utils
- AesKeyGenerator
- TwofishKeyGenerator
- ChaCha20KeyGenerator
- DesEdeKeyGenerator
- JwtSecretGenerator
---

## Configuration — Environment Variables

| Variable                       | Description                                      | Default                                 |
|--------------------------------|--------------------------------------------------|-----------------------------------------|
| `N1NETAILS_DATABASE_ENABLED`   | Enables or disables the database connection      | `false`                                 |
| `POSTGRES_URL`                 | Postgres database url                            | `jdbc:postgresql://localhost/n1netails` |
| `POSTGRES_USERNAME`            | Postgres user                                    | `n1netails`                             |
| `POSTGRES_PASSWORD`            | Postgres user password                           | `n1netails`                             |
| `N1NETAILS_ENCRYPTION_OLD_KEY` | The old encryption key to be used for decryption | `add-old-encryption-key`                |
| `N1NETAILS_ENCRYPTION_NEW_KEY` | The new encryption key to be used for encryption | `add-new-encryption-key`                |
| `N1NETAILS_KEY_ROTATION_TYPE`  | The type of key rotation to be used              | `AES_TO_AES`                            |

---

#### Quick Start

1. **Clone the repository** (if you haven’t already):

   ```bash
   git clone https://github.com/n1netails/n1netails-cipher-kitsune.git
   cd n1netails-cipher-kitsune
   ```

### Deployment with Jar file
Requires java 17 to be installed on your server
```bash
java -jar target/n1netails-cipher-kitsune.jar
```

---

## Development

### Build the project

```bash
mvn clean install
```

### Run with environment variables

```bash
mvn spring-boot:run
```

---

## Support & Community

For help or to discuss:

* Open a GitHub issue
* Join our Discord community

[![Join our Discord](https://img.shields.io/badge/Join_Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/ma9CCw7F2x)

---

## Contributing

We welcome contributions!
Please follow our [CONTRIBUTING.md](./contributing.md) guidelines.

