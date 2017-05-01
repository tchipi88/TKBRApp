(function() {
    'use strict';

    angular
        .module('app')
        .factory('CompteAnalytiqueFournisseurSearch', CompteAnalytiqueFournisseurSearch);

    CompteAnalytiqueFournisseurSearch.$inject = ['$resource'];

    function CompteAnalytiqueFournisseurSearch($resource) {
        var resourceUrl =  'api/_search/compte-analytique-fournisseurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
