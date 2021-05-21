(ns segunda-semana.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/compras")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco []
  (d/delete-database db-uri))

(defn todas-as-compras [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :compra/id ]] db))

(defn todos-os-clientes [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :cliente/id  ]] db))

(defn todos-os-cartoes [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :cartao/id  ]] db))

(defn todos-as-categorias [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :categoria/id  ]] db))

(defn cria-schema [conn]
  (d/transact conn schema-cliente)
  (d/transact conn schema-cartao)
  (d/transact conn schema-compra)
  (d/transact conn schema-categoria))

(def schema-compra [{
              :db/ident         :compra/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity}
             {:db/ident         :compra/data
              :db/valueType     :db.type/instant
              :db/cardinality   :db.cardinality/one
              :db/doc           "Data que a compra foi realizada"
              }
             {:db/ident         :compra/valor
              :db/valueType     :db.type/bigdec
              :db/cardinality   :db.cardinality/one
              :db/doc           "Valor da compra"
              }
             {:db/ident         :compra/estabelecimento
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "Nome do estabelecimento"
              }
             {:db/ident         :compra/categoria
              :db/valueType     :db.type/ref
              :db/cardinality   :db.cardinality/one
              :db/doc           "Categoria da compra"
              }
             {
              :db/ident         :compra/cartao
              :db/valueType     :db.type/ref
              :db/cardinality   :db.cardinality/one
              }])

(def schema-cliente [{
              :db/ident         :cliente/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity
              }
             {
              :db/ident         :cliente/nome
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "Nome do cliente"
              }
             {
              :db/ident         :cliente/sobrenome
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "Sobrenome do cliente"
              }
             {
              :db/ident         :cliente/cpf
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity
              :db/doc           "CPF do cliente"
              }
             {
              :db/ident         :cliente/email
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity
              :db/doc           "Email do cliente"
              }])

(def schema-cartao [{
              :db/ident         :cartao/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity
              }
             {
              :db/ident         :cartao/numero
              :db/valueType     :db.type/long
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity
              }
             {
              :db/ident         :cartao/cvv
              :db/valueType     :db.type/long
              :db/cardinality   :db.cardinality/one
              }
             {
              :db/ident         :cartao/validade
              :db/valueType     :db.type/instant
              :db/cardinality   :db.cardinality/one
              }
             {
              :db/ident         :cartao/limite
              :db/valueType     :db.type/bigdec
              :db/cardinality   :db.cardinality/one
              }
             {
              :db/ident         :cartao/cliente
              :db/valueType     :db.type/ref
              :db/cardinality   :db.cardinality/one
              }])

(def schema-categoria [{
              :db/ident         :categoria/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity
              }
             {
              :db/ident         :categoria/slug
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              }
             {
              :db/ident         :categoria/nome
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "Categoria de compras"
              }])

(def cliente (model/novo-cliente "Jose" "Silva" "1010101010" "jose@jose.com"))
(def cliente1 (model/novo-cliente "Orlando" "Oliveira" "1212121212" "orlando@orlando.com"))


(def cartao (model/novo-cartao 5336742783042220 430 #inst "2022-09-16T00:00" 8000M [:cliente/id (:cliente/id cliente)]))


(def categoria (model/nova-categoria "Supermercado" "/supermercado"))
(def categoria1 (model/nova-categoria "Farmacia" "/farmacia"))
(def categoria2 (model/nova-categoria "Padaria" "/padaria"))

(def compra (model/nova-compra #inst "2020-09-16T07:01",
                               10.8M,
                               "Supermercado BH",
                               [:categoria/id (:categoria/id categoria)],
                               [:cartao/id (:cartao/id cartao)]))

(def compra1 (model/nova-compra #inst "2021-04-17T22:31",
                               15.2M,
                               "Pao fofo",
                               [:categoria/id (:categoria/id categoria2)],
                               [:cartao/id (:cartao/id cartao)]))

(def compra2 (model/nova-compra #inst "2020-12-02T20:31",
                               102.9M,
                               "Carrefour",
                               [:categoria/id (:categoria/id categoria)],
                               [:cartao/id (:cartao/id cartao)]))

(defn insere-dados [conn]
  (d/transact conn [cliente, cliente1])
  (d/transact conn [cartao])
  (d/transact conn [categoria, categoria1, categoria2])
  (d/transact conn [compra compra1 compra2])
  )

