(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .factory('ProduitFournisseurSearch', ProduitFournisseurSearch);

    ProduitFournisseurSearch.$inject = ['$resource'];

    function ProduitFournisseurSearch($resource) {
        var resourceUrl =  'api/_search/produit-fournisseurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();