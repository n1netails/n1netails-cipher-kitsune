# N1netails

<div align="center">
  <img src="https://github.com/n1netails/n1netails-cipher-kitsune/blob/v0.0.5/kitsune-v0.0.5.jpg" alt="N1netails" width="500" style="display: block; margin: auto;"/>
</div>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Cipher Kitsune — N1netails Encryption Cipher Utilities

Cipher Kitsune is a production-ready, modular **Encryption-as-a-Service (EaaS)** platform and utility.

## 🚀 Encryption-as-a-Service (EaaS) API

Cipher Kitsune provides a comprehensive REST API for encryption, hashing, and encoding.

### Core Features:
- **Modular Strategy Pattern:** Easily add or rotate encryption algorithms.
- **Configuration-Driven:** Toggle algorithms via feature flags without code changes.
- **Secure by Default:** No logging of sensitive data (plaintext, keys, hashes).
- **Rate Limited:** Built-in abuse protection for public endpoints.
- **Swagger Documentation:** Interactive API docs with Dark Mode and Kitsune theme.

### Supported Algorithms:
- **Symmetric:** AES-GCM, ChaCha20-Poly1305
- **Asymmetric:** RSA (OAEP), EC (ECIES)
- **Hashing:** SHA-256, SHA-512, Argon2, BCrypt
- **Encoding:** Base64, Hex

### API Endpoints:
- `POST /api/v1/encrypt` - Encrypt plaintext
- `POST /api/v1/decrypt` - Decrypt ciphertext
- `POST /api/v1/hash` - Generate secure hashes
- `POST /api/v1/encode` - Encode data
- `POST /api/v1/decode` - Decode data
- `GET /api/v1/generate/key/{algorithm}` - Generate a symmetric key (AES, ChaCha20, Twofish, JWT)
- `GET /api/v1/generate/keypair/{algorithm}` - Generate an asymmetric key pair (RSA, EC)
- `GET /api/v1/generate/iv` - Generate a random IV

---

## ⚡ Quick Start Examples

### Base64 Encode
```bash
curl -X POST http://localhost:9905/api/v1/encode \
     -H "Content-Type: application/json" \
     -d '{"data": "Hello Kitsune", "algorithm": "Base64"}'
```

### AES Encrypt
```bash
curl -X POST http://localhost:9905/api/v1/encrypt \
     -H "Content-Type: application/json" \
     -d '{
       "data": "Secret Message",
       "algorithm": "AES",
       "key": "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=",
       "iv": "MTIzNDU2Nzg5MDEy"
     }'
```

### RSA Encrypt
```bash
curl -X POST http://localhost:9905/api/v1/encrypt \
     -H "Content-Type: application/json" \
     -d '{
       "data": "Secret Message",
       "algorithm": "RSA-OAEP",
       "key": "-----BEGIN PUBLIC KEY-----\n..."
     }'
```

### SHA-256 Hashing
```bash
curl -X POST http://localhost:9905/api/v1/hash \
     -H "Content-Type: application/json" \
     -d '{"data": "Kitsune", "algorithm": "SHA-256"}'
```

---

## 🎨 API Documentation

Interactive Swagger documentation is available at:
`http://localhost:9905/swagger-ui.html`

The documentation features a **dark mode** theme with the signature Kitsune accent color (`#F06D0F`).

---

## Key Rotation Services

The following key rotation services are available:

- `AES_TO_AES` - Rotates data from an old AES key to a new AES key.
- `DES_TO_AES` - Rotates data from an old DES key to a new AES key.
- `AES_CTR` - Rotates data from an old AES-CTR key to a new AES-CTR key.
- `CHACHA20` - Rotates data from an old ChaCha20 key to a new ChaCha20 key.
- `TWOFISH` - Rotates data from an old Twofish key to a new Twofish key.
- `DES_EDE_TO_AES` - Rotates data from an old 3DES (DESede) key to a new AES key.

## 🔐 Generating Keys

Kitsune provides utility endpoints to generate cryptographically strong keys and vectors.

### 1. JWT Secret Key (HMAC SHA-256)
- **When to use:** Used for signing and verifying JSON Web Tokens.
- **Recommended size:** 256-bit (32 bytes) or higher.
- **Curl Example:**
  ```bash
  curl -X GET http://localhost:9905/api/v1/generate/key/jwt
  ```
- **Example Response:**
  ```json
  {
    "success": true,
    "algorithm": "JWT",
    "result": "bXktc2VjdXJlLWp3dC1zZWNyZXQta2V5LTMyLWJ5dGVzLWxvbmc=",
    "timestamp": "2023-10-27T10:00:00"
  }
  ```

### 2. AES Secret Key
- **When to use:** Symmetric encryption (AES-GCM, AES-CBC).
- **Recommended size:** 256-bit (32 bytes).
- **Curl Example:**
  ```bash
  curl -X GET http://localhost:9905/api/v1/generate/key/aes
  ```

### 3. RSA Key Pair
- **When to use:** Asymmetric encryption, digital signatures.
- **Recommended size:** 2048-bit or 4096-bit.
- **Curl Example:**
  ```bash
  curl -X GET http://localhost:9905/api/v1/generate/keypair/rsa
  ```
- **Example Response:** Returns a JSON object with `publicKey` and `privateKey` in PEM format.

### 4. Initialization Vector (IV)
- **When to use:** Required for symmetric encryption modes like GCM or CBC to ensure uniqueness.
- **Recommended size:** 12 bytes for AES-GCM.
- **Curl Example:**
  ```bash
  curl -X GET "http://localhost:9905/api/v1/generate/iv?length=12"
  ```

---

## Configuration — Environment Variables

| Variable                       | Description                                      | Default                                 |
|--------------------------------|--------------------------------------------------|-----------------------------------------|
| `N1NETAILS_DATABASE_ENABLED`       | Enables or disables the database connection      | `false`                                 |
| `POSTGRES_URL`                     | Postgres database url                            | `jdbc:postgresql://localhost/n1netails` |
| `POSTGRES_USERNAME`                | Postgres user                                    | `n1netails`                             |
| `POSTGRES_PASSWORD`                | Postgres user password                           | `n1netails`                             |
| `N1NETAILS_ENCRYPTION_OLD_KEY`     | The old encryption key to be used for decryption | `MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=` |
| `N1NETAILS_ENCRYPTION_NEW_KEY`     | The new encryption key to be used for encryption | `MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=` |
| `N1NETAILS_KEY_ROTATION_TYPE`      | The type of key rotation to be used              | `AES_TO_AES`                            |
| `N1NETAILS_ENCRYPTION_AES_GCM_ENABLED` | Enable/Disable AES-GCM strategy              | `true`                                  |
| `N1NETAILS_ENCRYPTION_CHACHA20_ENABLED`| Enable/Disable ChaCha20 strategy             | `true`                                  |
| `N1NETAILS_HASHING_ARGON2_ENABLED` | Enable/Disable Argon2 hashing                    | `true`                                  |
| `N1NETAILS_RATELIMIT_CAPACITY`     | API Rate limit bucket capacity                   | `100`                                   |
| `N1NETAILS_RATELIMIT_TPS`          | API Rate limit tokens per second                 | `10`                                    |

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

