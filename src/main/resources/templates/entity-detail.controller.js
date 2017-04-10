(function() {
    'use strict';

    angular
        .module('app')
        .controller('[(${entity})]DetailController', [(${entity})]DetailController);

    [(${entity})]DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', '[(${entity})]' [(${entity_selects_header})]];

    function [(${entity})]DetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, [(${entity})] [(${entity_selects_header1})]) {
        var vm = this;

        vm.[(${entity_var})] = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:[(${entity_var})]Update', function(event, result) {
            vm.[(${entity_var})] = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
