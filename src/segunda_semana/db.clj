(ns segunda-semana.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/compras")

(defn abre-conexao! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco! []
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

(defn cria-schema! [conn]
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



