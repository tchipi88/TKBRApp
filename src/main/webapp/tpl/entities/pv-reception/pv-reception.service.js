(function() {
    'use strict';
    angular
        .module('tkbrApp')
        .factory('PvReception', PvReception);

    PvReception.$inject = ['$resource', 'DateUtils'];

    function PvReception ($resource, DateUtils) {
        var resourceUrl =  'api/pv-receptions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateReception = DateUtils.convertLocalDateFromServer(data.dateReception);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateReception = DateUtils.convertLocalDateToServer(copy.dateReception);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateReception = DateUtils.convertLocalDateToServer(copy.dateReception);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
