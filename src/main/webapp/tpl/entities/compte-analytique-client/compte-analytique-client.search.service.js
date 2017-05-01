(function() {
    'use strict';

    angular
        .module('app')
        .factory('CompteAnalytiqueClientSearch', CompteAnalytiqueClientSearch);

    CompteAnalytiqueClientSearch.$inject = ['$resource'];

    function CompteAnalytiqueClientSearch($resource) {
        var resourceUrl =  'api/_search/compte-analytique-clients/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
