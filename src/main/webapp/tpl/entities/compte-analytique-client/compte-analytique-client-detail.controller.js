(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueClientDetailController', CompteAnalytiqueClientDetailController);

    CompteAnalytiqueClientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CompteAnalytiqueClient' ,'Client'];

    function CompteAnalytiqueClientDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CompteAnalytiqueClient ,Client) {
        var vm = this;

        vm.compteAnalytiqueClient = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:compteAnalytiqueClientUpdate', function(event, result) {
            vm.compteAnalytiqueClient = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
