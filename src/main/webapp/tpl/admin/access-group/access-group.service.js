(function() {
    'use strict';
    angular
        .module('app')
        .factory('AccessGroup', AccessGroup);

    AccessGroup.$inject = ['$resource'];

    function AccessGroup ($resource) {
        var resourceUrl =  'api/access-groups/:id';

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
