(function() {
    'use strict';

    angular
        .module('app')
        .factory('TerrainSearch', TerrainSearch);

    TerrainSearch.$inject = ['$resource'];

    function TerrainSearch($resource) {
        var resourceUrl =  'api/_search/terrains/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
