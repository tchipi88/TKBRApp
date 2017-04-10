(function() {
    'use strict';

    angular
        .module('app')
        .factory('VenteSearch', VenteSearch);

    VenteSearch.$inject = ['$resource'];

    function VenteSearch($resource) {
        var resourceUrl =  'api/_search/ventes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
