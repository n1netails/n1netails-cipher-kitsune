# N1netails

<div align="center">
  <img src="https://raw.githubusercontent.com/n1netails/n1netails/refs/heads/main/n1netails_icon_transparent.png" alt="N1ne Tails" width="500" style="display: block; margin: auto;"/>
</div>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Cipher Kitsune — Real-time Log Tailing & Alerting Service

Cipher Kitsune is an encryption utility related to n1netails.
Supported Features:
- AES key generator
- AES key rotation
- Can be used to help rotate encrypted values used within the n1netails application.

---

## Getting Started with Cipher Kitsune


---

## Configuration — Environment Variables

| Variable                                    | Description                                    | Default                               |
|---------------------------------------------|------------------------------------------------|---------------------------------------|
| `POSTGRES_URL`                              | Postgres database url                          | jdbc:postgresql://localhost/n1netails |
| `POSTGRES_USERNAME`                         | Postgres user                                  | n1netails                             |
| `POSTGRES_PASSWORD`                         | Postgres user password                         | n1netails                             |
| `N1NETAILS_ENCRYPTION_OLD_KEY`              | URL endpoint to send alert data                | add-old-encryption-key                |
| `N1NETAILS_ENCRYPTION_NEW_KEY`              | Optional token for authentication (n1ne-token) | add-new-encryption-key                |

---

#### Quick Start

1. **Clone the repository** (if you haven’t already):

   ```bash
   git clone https://github.com/n1netails/n1netails-cipher-kitsune.git
   cd n1netails-cipher-kitsune
   ```

2.

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

