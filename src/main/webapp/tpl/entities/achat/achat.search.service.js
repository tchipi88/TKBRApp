(function() {
    'use strict';

    angular
        .module('app')
        .factory('AchatSearch', AchatSearch);

    AchatSearch.$inject = ['$resource'];

    function AchatSearch($resource) {
        var resourceUrl =  'api/_search/achats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
