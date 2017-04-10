(function() {
    'use strict';
    angular
        .module('app')
        .factory('BonReceptionLigne', BonReceptionLigne);

    BonReceptionLigne.$inject = ['$resource'];

    function BonReceptionLigne ($resource) {
        var resourceUrl =  'api/bon-reception-lignes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
