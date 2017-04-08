(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .factory('FournisseurSearch', FournisseurSearch);

    FournisseurSearch.$inject = ['$resource'];

    function FournisseurSearch($resource) {
        var resourceUrl =  'api/_search/fournisseurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
