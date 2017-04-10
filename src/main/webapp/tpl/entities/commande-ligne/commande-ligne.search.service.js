(function() {
    'use strict';

    angular
        .module('app')
        .factory('CommandeLigneSearch', CommandeLigneSearch);

    CommandeLigneSearch.$inject = ['$resource'];

    function CommandeLigneSearch($resource) {
        var resourceUrl =  'api/_search/commande-lignes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
