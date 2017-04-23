(function() {
    'use strict';

    angular
        .module('app')
        .factory('LocalSearch', LocalSearch);

    LocalSearch.$inject = ['$resource'];

    function LocalSearch($resource) {
        var resourceUrl =  'api/_search/locals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
