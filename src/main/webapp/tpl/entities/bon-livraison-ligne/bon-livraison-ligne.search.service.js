(function() {
    'use strict';

    angular
        .module('app')
        .factory('BonLivraisonLigneSearch', BonLivraisonLigneSearch);

    BonLivraisonLigneSearch.$inject = ['$resource'];

    function BonLivraisonLigneSearch($resource) {
        var resourceUrl =  'api/_search/bon-livraison-lignes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
