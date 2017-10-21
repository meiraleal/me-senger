(ns paratydigital.db)

(def initial-route {:panel :home-panel})

(def default-db
  {:title "Paraty Digital"
   :back-button false
   :active-route initial-route
   :history [initial-route]
   :args nil
   :user {:_id 22222
          :name "Alan Leal"
          :avatar "https://facebook.github.io/react/img/logo_og.png"}
   :home-highlights [{:key 0
                      :name "Destaque"}
                     {:key 1
                      :name "Bombando Agora"}
                     {:key 2
                      :name "Novidade"}
                     {:key 3
                      :name "Mais Vistos"}]
   :categories [{:id 0
                 :name "Comida"
                 :icon "restaurant"
                 :color :lightGreen700}
                {:id 1
                 :name "Hospedagem"
                 :icon "hotel"
                 :color :lightGreen700}
                {:id 2
                 :name "Passeios"
                 :icon "beach-access"
                 :color :red400}
                {:id 3
                 :name "Eventos"
                 :icon "local-bar"
                 :color :orange800}
                {:id 4
                 :name "A Cidade"
                 :icon "terrain"
                 :color :blue600}
                {:id 5
                 :name "Anuncios e Vagas"
                 :icon "directions-car"
                 :color :blue500}]

   :ads [{:id 0
          :name "Lojas de Paraty"
          :headline "Anunciam aqui"
          :category 0}
         {:id 1
          :name "Che Lagarto"
          :headline "O melhor hostel"
          :category 1}
         {:id 2
          :name "Antiquaria Restaurante"
          :headline "paraty do passado"
          :category 1}
         {:id 3
          :name "Cafe no bule"
          :headline "melhor que o do ratinho"
          :category 2}
         {:id 4
          :name "Chapelaria chapeleu"
          :headline "Melhor que o zebeleu"
          :category 1}
         {:id 5
          :name "Hortifruti Paraty"
          :headline "e Para todo mundo"
          :category 2}]

   :threads
   [
    {:id 0
     :name "Guia de Paraty"
     :messages [{:_id 1
                 :text "Bom dia!"
                 :createdAt (js/Date.)}
                {:_id 2
                 :text "Seja bem-vindo(a) ao Paraty Digital! Eu serei o seu guia por aqui :)"
                 :createdAt (js/Date.)}]}]})
