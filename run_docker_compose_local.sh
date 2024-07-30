#!/bin/bash

# Define o nome do arquivo de log
LOG_FILE="docker-compose-local.log"

# Remove o arquivo de log anterior se existir
if [ -f "$LOG_FILE" ]; then
    rm "$LOG_FILE"
fi

# Executa o docker-compose e redireciona a saída para o arquivo de log
if docker-compose -f docker-compose-local.yaml up -d &> "$LOG_FILE"; then
    # Mensagem de sucesso
    echo "Docker Compose executado com sucesso. Favor executar a aplicação em sua IDE. Banco de dados disponível."
else
    # Mensagem de erro
    echo "Erro ao executar o Docker Compose. Verifique o arquivo de log [docker-compose-local.log] para mais detalhes."
    # Opcionalmente, você pode exibir o conteúdo do log no terminal
    # cat "$LOG_FILE"
fi
