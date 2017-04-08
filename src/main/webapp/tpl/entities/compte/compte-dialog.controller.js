(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('CompteDialogController', CompteDialogController);

    CompteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Compte'];

    function CompteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Compte) {
        var vm = this;

        vm.compte = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compte.id !== null) {
                Compte.update(vm.compte, onSaveSuccess, onSaveError);
            } else {
                Compte.save(vm.compte, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:compteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
