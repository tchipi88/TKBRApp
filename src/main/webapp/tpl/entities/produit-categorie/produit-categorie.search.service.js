(function() {
    'use strict';

    angular
        .module('app')
        .factory('ProduitCategorieSearch', ProduitCategorieSearch);

    ProduitCategorieSearch.$inject = ['$resource'];

    function ProduitCategorieSearch($resource) {
        var resourceUrl =  'api/_search/produit-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
