package com.example.myapplication.security

/**
 * https://www.youtube.com/watch?v=j9QmMEWmcfo
 * https://chat.openai.com/share/d8e257ad-f6fd-41e7-87ae-4bc09e96b0ed
 * https://chat.openai.com/share/a64dbfee-82d4-4abf-a370-76619f0801a3
 *
 * As https uses tls where it transfers keys based on encryption types.
 * Does it use hashing also anywhere while establishing https communication ?
 *
 * HTTPS (Hypertext Transfer Protocol Secure) primarily relies on the Transport Layer Security (TLS) protocol to
 * secure communication between a client (e.g., a web browser) and a server. While TLS does involve encryption,
 * it doesn't use hashing in the same way that it uses encryption.
 *
 * Here's a brief overview of the key steps in the TLS handshake process, which establishes a secure connection
 * between the client and server:
 *
 * ClientHello: The client initiates the connection by sending a message called ClientHello, indicating the
 * supported cryptographic algorithms and other parameters.
 *
 * ServerHello: The server responds with a message called ServerHello, in which it selects the most
 * suitable encryption algorithm and other parameters from the options provided by the client.
 *
 * Key Exchange: This step involves the exchange of key information between the client and server,
 * typically through a process that may involve public-key cryptography (asymmetric encryption) or
 * a pre-shared secret.
 *
 * Finished: After the key exchange, both the client and server send a Finished message, confirming that the
 * handshake is complete, and communication can proceed securely.
 *
 * Hash functions are not used directly in the TLS handshake for key exchange or encryption. However, hash
 * functions are employed in other aspects of the TLS protocol, particularly for data integrity and authentication.
 * Here are a couple of instances where hashing is used:
 *
 * Message Authentication Codes (MACs): TLS uses hash functions to generate Message Authentication Codes,
 * which are used to ensure the integrity of transmitted data. The MAC is calculated using a shared secret
 * key between the client and server.
 * Client Side:
 * The client sends DATA to the server.
 * To ensure integrity, the client computes a MAC using a hash function like HMAC:
 * plaintext
 * Copy code
 * MAC = HMAC(SECRET_KEY, DATA)
 * The client sends both DATA and the computed MAC to the server.
 *
 * Server Side:
 * The server receives DATA and MAC.
 * The server computes its own MAC using the same KEY and DATA:
 * plaintext
 * Copy code
 * MAC' = HMAC(SECRET_KEY, DATA)
 * If the MAC sent by the client matches the MAC computed by the server (MAC == MAC'), the server knows the data was not altered during transmission.
 *
 * Digital Signatures: In some cases, digital signatures are used for authentication. These signatures
 * involve the use of hash functions to create a digest of the signed data, which is then encrypted with
 * a private key.
 *
 * While hashing is integral to ensuring data integrity and authentication within TLS, it's not a direct part of
 * the key exchange or encryption processes during the TLS handshake.
 *
 * *********************************************************************************************************************
 *  * *********************************************************************************************************************
 * * *********************************************************************************************************************
 * *********************************************************************************************************************
 * * what about symmetric encryption. Does https uses symmetric encryption also along with asymmetric ?
 *
 * Yes, HTTPS typically uses a combination of asymmetric (public-key) and symmetric-key encryption during the
 *  communication between a client and a server. This combination provides the benefits of both types of encryption.
 *
 * Asymmetric Encryption (Public-Key Cryptography): Asymmetric encryption is used during the initial stages of
 * the TLS handshake for key exchange and authentication. The server has a pair of public and private keys.
 * The public key is used to encrypt information, and only the corresponding private key can decrypt it.
 * The server sends its public key to the client during the handshake, and the client uses it to encrypt
 * a pre-master secret, which is then sent back to the server. This pre-master secret is crucial for deriving the
 * shared symmetric encryption keys.
 *
 * Key Derivation and Symmetric Encryption: Once the pre-master secret is exchanged securely, both the client
 * and server independently derive a shared secret key from this pre-master secret. This shared secret is then
 * used for symmetric-key encryption, where the same key is used for both encryption and decryption. Symmetric
 * encryption is more computationally efficient than asymmetric encryption, which is why it's used for the bulk of
 * the data transfer.
 *
 * Symmetric Encryption for Data Transfer: After the initial handshake, most of the actual data transfer between
 * the client and server is encrypted using symmetric-key encryption. This ensures the confidentiality and
 * integrity of the data during transit. Common symmetric encryption algorithms include AES (Advanced Encryption
 * Standard).
 *
 * So, in summary, HTTPS uses a combination of asymmetric encryption for secure key exchange and authentication
 * during the handshake, and symmetric encryption for the actual secure data transfer between the client and server.
 * This combination provides a good balance of security and efficiency.
 *
 * *********************************************************************************************************************
 *  * *********************************************************************************************************************
 * * *********************************************************************************************************************
 * ********************************************************************************************************************* *
 * what is the loophole if we use only symmetric encryption as it it light weight
 * While symmetric encryption is generally more efficient than asymmetric encryption, it comes with a key distribution challenge. The primary
 * disadvantage of using only symmetric encryption is the need to securely share and manage the secret key between the communicating parties.
 *
 * Here are some considerations and challenges:
 *
 * Key Distribution: For secure communication, both the client and the server need to share a secret key.
 * If an eavesdropper intercepts this key during transmission or if there's a compromise in key distribution,
 * the entire security of the system is compromised.
 *
 * Key Management: Symmetric encryption requires careful key management. This includes generating strong random keys,
 * securely storing and distributing them, and rotating keys periodically. Managing keys at scale and ensuring they
 * remain confidential is a non-trivial task.
 *
 * Scalability: Symmetric encryption becomes challenging in scenarios where there are many communicating parties.
 * Each pair of parties needs a unique shared key, leading to a quadratic increase in the number of keys to
 * manage.
 *
 * Forward Secrecy: Symmetric encryption alone doesn't provide forward secrecy. If the key is compromised
 * at any point in time, it allows an adversary to decrypt all previously captured traffic.
 *
 * Authentication: Symmetric encryption alone doesn't provide a built-in mechanism for entity authentication.
 * While the data is confidential, it doesn't necessarily ensure that the data is coming from a trusted source.
 *
 * Initial Key Exchange: The initial exchange of the symmetric key needs to be secured. If an attacker can
 * compromise this initial exchange, they can gain access to the symmetric key and decrypt all subsequent
 * communication.
 *
 * This is why the hybrid approach used in HTTPS, combining the efficiency of symmetric encryption with the
 * secure key exchange of asymmetric encryption during the TLS handshake, provides a more robust solution.
 * Asymmetric encryption addresses the key distribution and initial key exchange challenges, while symmetric
 * encryption takes care of the bulk of the data transfer efficiently.
 *
 * *********************************************************************************************************************
 *  * *********************************************************************************************************************
 * * *********************************************************************************************************************
 * *********************************************************************************************************************
 * Now certificate pinning, why is it needed ?
 * HTTPS uses asymmetric encryption during the initial handshake to establish a secure connection between a
 * client and a server. The server presents a digital certificate(which contains public key) during this
 * handshake to prove its identity,
 * and the client verifies the authenticity of the certificate by checking the certificate chain and ensuring it
 * is signed by a trusted Certificate Authority (CA).
 * Remember middle man can impose his own some valid certificate and client can trust on him in case of absence of
 * certificate pinning and now middle man can establish a connection with client. Even if its asymmetric encryption
 * based but at the end client is communicating with un trusted man.
 *
 *
 * both client server have public/private key
 *
 */
class EncryptionSecurity {
}

/**
 * from very high level like whether it follows asymetric or symetric or both ? What's diff in ssl vs tls
 * or both follows same process from high level point of view
 *
 * ChatGPT
 * From a high-level point of view, both SSL (Secure Sockets Layer) and TLS (Transport Layer Security)
 * use a combination of asymmetric and symmetric encryption, but there are differences in their processes and security features. Here's an overview:
 *
 * High-Level Encryption Process
 * Asymmetric Encryption:
 *
 * Used during the initial handshake to securely exchange keys.
 * Involves a pair of keys: a public key and a private key.
 * The public key is used to encrypt data, and the private key is used to decrypt it.
 * Symmetric Encryption:
 *
 * Used for encrypting the actual data transferred during the session.
 * Involves a single key that both parties use to encrypt and decrypt data.
 * This key is securely exchanged using asymmetric encryption during the handshake.
 * SSL and TLS Process (High-Level)
 *
 * SSL
 * Handshake:
 *
 * Client and server exchange hello messages to establish connection parameters.
 * Server sends its certificate to the client, which contains the server's public key.
 * Client generates a pre-master secret, encrypts it with the server's public key, and sends it to the server.
 * Both client and server generate the session keys from the pre-master secret.
 * Session Key Exchange:
 *
 * The session key, derived from the pre-master secret, is used for symmetric encryption of data.
 * SSL supports various cipher suites, some of which are now considered insecure.
 * Data Transfer:
 *
 * Data is encrypted using the session key for confidentiality.
 * Message authentication is performed using weaker hash functions like MD5 or SHA-1.
 *
 * ********
 *
 * TLS
 * Handshake:
 *
 * Similar to SSL, but with improvements in the exchange process to prevent certain types of attacks.
 * Server and client exchange hello messages, and the server sends its certificate.
 * The client and server negotiate the best cipher suite to use.
 * The client generates a pre-master secret, encrypts it with the server's public key, and sends it to the server.
 * Both client and server derive the session keys from the pre-master secret.
 * Session Key Exchange:
 *
 * The session key is used for symmetric encryption of data.
 * TLS supports modern, more secure cipher suites.
 * TLS 1.3 simplifies the handshake process and removes support for older, less secure cipher suites.
 * Data Transfer:
 *
 * Data is encrypted using the session key for confidentiality.
 * Message authentication is performed using stronger hash functions like SHA-256.
 * TLS 1.3 uses authenticated encryption with associated data (AEAD) cipher suites, enhancing security.
 * Differences Between SSL and TLS (High-Level)
 * Handshake Improvements:
 *
 * TLS has a more secure and efficient handshake process compared to SSL.
 * TLS 1.3 further reduces the number of round trips required to establish a secure connection.
 * Cipher Suites:
 *
 * TLS supports more secure and modern cipher suites compared to SSL.
 * TLS 1.3 exclusively uses AEAD cipher suites, enhancing security.
 * Hash Functions:
 *
 * TLS uses stronger hash functions for message authentication, whereas SSL used weaker ones like MD5 and SHA-1.
 * Protocol Flexibility:
 *
 * TLS is designed to be more extensible and adaptable to new security practices and algorithms.
 * Security Features:
 *
 * TLS includes better protection against certain types of attacks (e.g., downgrade attacks, padding oracle attacks).
 * TLS 1.3 removes obsolete and insecure features found in earlier versions of TLS and SSL.
 * In summary, both SSL and TLS use a combination of asymmetric and symmetric encryption, but TLS provides significant improvements in
 * security, efficiency, and flexibility over SSL.
 *
 *
 *
 *
 *
 *
 *
 */

/**
 * Digital Signatures: Used for authenticating and ensuring the integrity of data. They rely on asymmetric cryptography and are crucial for
 * security beyond the initial handshake.
 *
 * I see where the confusion might be coming from. Let’s clarify how digital signatures work in practice, particularly regarding key management and
 * the role of the public/private keys:
 *
 * Digital Signatures: Key Management
 * Key Generation:
 *
 * Client: The client generates its own public/private key pair if it's going to use digital signatures. This key pair is typically managed by the
 * client’s system or application.
 * Server: Similarly, the server generates its own public/private key pair for its own purposes.
 * Digital Signature Process:
 *
 * Client (Signing Data):
 *
 * Hashing: The client creates a hash of the data.
 * Signing: The client encrypts this hash with its own private key to create the digital signature. This private key is generated and managed by the client.
 * The client sends both the data and the digital signature to the server.
 * Server (Verifying Data):
 *
 * Hashing: The server computes its own hash of the received data.
 * Decrypting: The server uses the client’s public key to decrypt the digital signature, which reveals the hash that the client signed.
 * Verification: The server compares the decrypted hash with the hash it computed. If they match, the data is confirmed as authentic and unaltered.
 * How the Server Gets the Client’s Public Key:
 * Public Key Distribution:
 *
 * In Practice: The client’s public key is typically distributed through a secure mechanism. This might be part of a digital certificate issued by a trusted
 * certificate authority (CA), or it could be shared through a secure channel or directory service.
 * Certificates:
 *
 * Digital Certificates: Often, public keys are distributed via digital certificates. The client might have a certificate from a CA that includes
 * its public key. The server can use this certificate to extract the client’s public key.
 * Key Exchange:
 *
 * Mutual TLS: In mutual TLS (mTLS), the client and server exchange certificates during the handshake. The server can use the client’s certificate
 * to obtain the public key.
 * Summary:
 * Client: Generates its own key pair. Uses its private key to sign data and creates a digital signature.
 * Server: Uses the client’s public key (obtained through certificates or secure distribution) to verify the digital signature.
 * This setup ensures that both parties can authenticate each other and verify the integrity of the transmitted data securely.
 */