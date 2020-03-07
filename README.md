# Architecture
L'architecture choisie est le MVVM qui est le nouveau standard recommandé par Google avec Dagger pour injecteur de dépendances.

#Solution
Nous avons choisi d'utiliser la pagination d'Android pour charger à la demander les photos dont les informations sont stockées avec room.
Puis nous utilisons glide our stoocker localement les photos pour un access offline

#Credits
- https://github.com/bumptech/glide/issues/4074
- https://proandroiddev.com/how-to-unit-test-code-with-coroutines-50c1640f6bef
