(ns segunda-semana.model)

(defn uuid [] (java.util.UUID/randomUUID))

(defn nova-compra [data-compra valor estabelecimento categoria cartao]
  {:compra/id               (uuid)
   :compra/data             data-compra
   :compra/valor            valor
   :compra/estabelecimento  estabelecimento
   :compra/categoria        categoria
   :compra/cartao           cartao})

(defn novo-cliente [nome sobrenome cpf email ]
  {:cliente/id               (uuid)
   :cliente/nome             nome
   :cliente/sobrenome        sobrenome
   :cliente/cpf              cpf
   :cliente/email            email})

(defn novo-cartao [numero cvv validade limite cliente ]
  {:cartao/id               (uuid)
   :cartao/numero           numero
   :cartao/cvv              cvv
   :cartao/validade         validade
   :cartao/limite           limite
   :cartao/cliente          cliente})

(defn nova-categoria [nome slug]
  {:categoria/id           (uuid)
   :categoria/slug         slug
   :categoria/nome         nome
   })
