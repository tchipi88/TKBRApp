(function() {
    'use strict';

    angular
        .module('app')
        .factory('EntrepotProduitSearch', EntrepotProduitSearch);

    EntrepotProduitSearch.$inject = ['$resource'];

    function EntrepotProduitSearch($resource) {
        var resourceUrl =  'api/_search/entrepot-produits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
