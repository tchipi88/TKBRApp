(function() {
    'use strict';
    angular
        .module('app')
        .factory('BonReception', BonReception);

    BonReception.$inject = ['$resource'];

    function BonReception ($resource) {
        var resourceUrl =  'api/bon-receptions/:id';

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
