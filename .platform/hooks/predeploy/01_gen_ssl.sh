#!/bin/bash
# Gera certificado SSL autoassinado para habilitar HTTPS na instância
set -e

SSL_DIR="/etc/nginx/ssl"

mkdir -p "$SSL_DIR"

if [ ! -f "$SSL_DIR/server.crt" ]; then
    openssl req -x509 -nodes -days 3650 -newkey rsa:2048 \
        -keyout "$SSL_DIR/server.key" \
        -out "$SSL_DIR/server.crt" \
        -subj "/C=BR/ST=SP/L=SaoPaulo/O=BobAThon/CN=bff-bob-a-thon"

    chmod 600 "$SSL_DIR/server.key"
    chmod 644 "$SSL_DIR/server.crt"

    echo "Certificado SSL gerado com sucesso."
else
    echo "Certificado SSL já existe, pulando geração."
fi
