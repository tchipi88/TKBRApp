(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .factory('EntrepotSearch', EntrepotSearch);

    EntrepotSearch.$inject = ['$resource'];

    function EntrepotSearch($resource) {
        var resourceUrl =  'api/_search/entrepots/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
