(function() {
    'use strict';
    angular
        .module('app')
        .factory('Access', Access);

    Access.$inject = ['$resource'];

    function Access ($resource) {
        var resourceUrl =  'api/accesss/:id';

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
